package com.project.biskit.service;

import com.project.biskit.entity.Items;
import com.project.biskit.entity.OrderItems;
import com.project.biskit.entity.Orders;
import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.AllItemsResponse;
import com.project.biskit.model.PlaceOrderRequest;
import com.project.biskit.model.StockCountProjection;
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
                itemDetail.getName(), itemDetail.getItemPrice()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> placeOrder(CustomUserDetail userDetail, List<PlaceOrderRequest> orderRequestList) throws BadRequestException, ConflictException {

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

            updateStockCount(orderRequestList, stockCount);
        } else throw new ConflictException(ResponseMessages.OUT_OF_STOCK);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    private void updateStockCount(List<PlaceOrderRequest> orderRequestList, Map<Long, Items> stockCount) {

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
