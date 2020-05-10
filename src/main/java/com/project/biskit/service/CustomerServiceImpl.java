package com.project.biskit.service;

import com.project.biskit.entity.Items;
import com.project.biskit.entity.OrderItems;
import com.project.biskit.entity.Orders;
import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.AllItemsResponse;
import com.project.biskit.model.OrderDetailResponse;
import com.project.biskit.model.PlaceOrderRequest;
import com.project.biskit.repository.ItemRepository;
import com.project.biskit.repository.OrderItemsRepository;
import com.project.biskit.repository.OrderRepository;
import com.project.biskit.security.CustomUserDetail;
import com.project.biskit.utils.ResponseMessages;
import com.project.biskit.utils.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Override
    public ResponseEntity<?> getAllItems(int pageNo, int pageSize) throws NotFoundException {

        Page<Items> itemsPage = itemRepository.findAll(PageRequest.of(pageNo - 1, pageSize));
        if (itemsPage.isEmpty())
            throw new NotFoundException(ResponseMessages.NO_ITEMS_FOUND);

        List<Items> itemsList = itemsPage.getContent().stream().map(items ->
                new Items(items.getId(), items.getName(), items.getItemPrice())).collect(Collectors.toList());

        return new ResponseEntity<>(new AllItemsResponse(itemsList, itemsPage.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getItemDetails(Long itemId) throws NotFoundException {

        Optional<Items> item = itemRepository.findById(itemId);

        Items itemDetail = item.orElseThrow(() -> new NotFoundException(ResponseMessages.NO_ITEMS_FOUND));

        return new ResponseEntity<>(new Items(itemDetail.getId(),
                itemDetail.getName(), itemDetail.getItemPrice(), itemDetail.getStockCount()), HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> placeOrder(CustomUserDetail userDetail, List<PlaceOrderRequest> orderRequestList) throws BadRequestException, ConflictException {

        if (Objects.isNull(orderRequestList) || orderRequestList.isEmpty())
            throw new BadRequestException(ResponseMessages.INVALID_ORDER_REQUEST);

        Map<Long, Long> requestItemsCount = new HashMap<>();
        List<Long> itemIds = new ArrayList<>();
        orderRequestList.forEach(requestList -> {
            requestItemsCount.put(requestList.getItemId(), requestList.getCount());
            itemIds.add(requestList.getItemId());
        });

        List<Items> stockCountList = itemRepository.getStockCount(itemIds);
        Map<Long, Items> stockCount = new HashMap<>();
        stockCountList.forEach(stock -> stockCount.put(stock.getId(), stock));

        if (inStock(requestItemsCount, stockCount)) {
            Orders order = new Orders();
            order.setUserId(userDetail.getId());
            order.setOrderStatus(Status.PROCESSING);

            Long orderId = orderRepository.save(order).getId();

            List<OrderItems> orderItems = new ArrayList<>();

            orderRequestList.forEach(item -> orderItems.add(new OrderItems(orderId, item.getItemId(),
                    item.getCount(), item.getCount() * stockCount.get(item.getItemId()).getItemPrice(),
                    Status.PROCESSING)));

            orderItemsRepository.saveAll(orderItems);

            updateStock(orderRequestList, stockCount);
        } else throw new ConflictException(ResponseMessages.OUT_OF_STOCK);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public synchronized ResponseEntity<?> cancelOrder(Long orderId) throws NotFoundException, BadRequestException {
        Optional<Orders> order = orderRepository.findById(orderId);
        Orders existingOrder = order.orElseThrow(() -> new NotFoundException(ResponseMessages.NO_SUCH_ORDER));

        if (existingOrder.getOrderStatus().equals(Status.CANCELLED))
            throw new BadRequestException(ResponseMessages.ORDER_ALREADY_CANCELLED);

        existingOrder.setOrderStatus(Status.CANCELLED);
        List<OrderItems> orderItems = orderItemsRepository.findByOrderId(orderId);
        orderItems.forEach(item -> item.setItemStatus(Status.CANCELLED));

        orderRepository.save(existingOrder);
        orderItemsRepository.saveAll(orderItems);

        updateStockAfterCancellation(orderItems);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> cancelItem(Long orderItemId) throws NotFoundException, BadRequestException {
        Optional<OrderItems> orderItems = orderItemsRepository.findById(orderItemId);
        OrderItems existingOrderItem = orderItems.orElseThrow(()
                -> new NotFoundException(ResponseMessages.NO_ORDER_ITEM_FOUND));

        if (existingOrderItem.getItemStatus().equals(Status.CANCELLED))
            throw new BadRequestException(ResponseMessages.ITEM_ALREADY_CANCELLED);

        existingOrderItem.setItemStatus(Status.CANCELLED);

        List<OrderItems> orderItemsList = orderItemsRepository.
                findRemainingOrderItems(existingOrderItem.getOrderId(), orderItemId);

        if (!(Objects.nonNull(orderItemsList) && !orderItemsList.isEmpty())){
            Optional<Orders> orders = orderRepository.findById(existingOrderItem.getOrderId());
            if (orders.isPresent()){
                Orders order = orders.get();
                order.setOrderStatus(Status.CANCELLED);
                orderRepository.save(order);
            }
        }
        orderItemsRepository.save(existingOrderItem);
        updateStockAfterCancellation(existingOrderItem.getItemId(), existingOrderItem.getCount());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllOrders(CustomUserDetail userDetail, int pageNo, int pageSize) throws NotFoundException {
        Page<Orders> orders = orderRepository.findByUserId(userDetail.getId(), PageRequest.of(pageNo - 1, pageSize));

        if (Objects.isNull(orders) || orders.isEmpty())
            throw new NotFoundException(ResponseMessages.NO_ORDERS);

        return new ResponseEntity<>(new AllItemsResponse(orders.getTotalElements(), orders.getContent()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getOrderDetails(Long id) throws NotFoundException, ConflictException {
        Optional<Orders> order = orderRepository.findById(id);
        Orders existingOrder = order.orElseThrow(() -> new NotFoundException(ResponseMessages.NO_SUCH_ORDER));

        List<OrderItems> orderItems = orderItemsRepository.findByOrderId(id);
        if (Objects.isNull(orderItems) || orderItems.isEmpty())
            throw new ConflictException(ResponseMessages.INVALID_ORDER);

        return new ResponseEntity<>(new OrderDetailResponse(existingOrder, orderItems), HttpStatus.OK);
    }

    private void updateStockAfterCancellation(Long itemId, Long count) {
        Optional<Items> items = itemRepository.findById(itemId);
        if (items.isPresent()){
            Items stockItem = items.get();
            stockItem.setStockCount(stockItem.getStockCount() + count);
            itemRepository.save(stockItem);
        }
    }

    private void updateStockAfterCancellation(List<OrderItems> orderItems) {
        List<Long> itemIds = new ArrayList<>();
        orderItems.forEach(item -> itemIds.add(item.getItemId()));

        Map<Long, Long> itemCount = new HashMap<>();
        orderItems.forEach(item -> itemCount.put(item.getItemId(), item.getCount()));

        List<Items> itemsList = itemRepository.getStockCount(itemIds);
        itemsList.forEach(item -> item.setStockCount(item.getStockCount() + itemCount.get(item.getId())));

        itemRepository.saveAll(itemsList);
    }

    private void updateStock(List<PlaceOrderRequest> orderRequestList, Map<Long, Items> stockCount) {

        List<Items> updatedItems = new ArrayList<>();
        orderRequestList.forEach(order -> {
            Items items = stockCount.get(order.getItemId());
            items.setStockCount(items.getStockCount() - order.getCount());
            updatedItems.add(items);
        });

        itemRepository.saveAll(updatedItems);
    }

    private boolean inStock(Map<Long, Long> requestItemsCount, Map<Long, Items> stockCount) {
        if (requestItemsCount.size() != stockCount.size())
            return false;
        return requestItemsCount.keySet().stream().noneMatch(itemId -> requestItemsCount.get(itemId) > stockCount.get(itemId).getStockCount());
    }
}
