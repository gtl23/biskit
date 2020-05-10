package com.project.biskit.service;

import com.project.biskit.entity.Items;
import com.project.biskit.entity.OrderItems;
import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.AddItemRequest;
import com.project.biskit.model.AllItemsResponse;
import com.project.biskit.repository.ItemRepository;
import com.project.biskit.repository.OrderItemsRepository;
import com.project.biskit.utils.ResponseMessages;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Autowired
    OrderItemsRepository orderItemsRepository;

    @Override
    public ResponseEntity<?> addItem(List<AddItemRequest> addItems) throws BadRequestException {

        if (Objects.isNull(addItems) || addItems.isEmpty())
            throw new BadRequestException(ResponseMessages.NO_ITEMS_TO_ADD);

        List<Items> items = addItems.stream().map(item ->
                new Items(item.getName(), item.getItemPrice(), item.getStockCount())).collect(Collectors.toList());
        itemRepository.saveAll(items);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Override
    public synchronized ResponseEntity<?> updateItem(Long id, AddItemRequest updateItem) throws NotFoundException {
        Optional<Items> items = itemRepository.findById(id);
        Items itemToUpdate = items.orElseThrow(() -> new NotFoundException(ResponseMessages.NO_ITEMS_FOUND));

        if (Objects.nonNull(updateItem.getItemPrice()))
            itemToUpdate.setItemPrice(updateItem.getItemPrice());

        if (Objects.nonNull(updateItem.getStockCount()))
            itemToUpdate.setStockCount(updateItem.getStockCount());

        if (Objects.nonNull(updateItem.getName()) && !updateItem.getName().isEmpty())
            itemToUpdate.setName(updateItem.getName());

        itemRepository.save(itemToUpdate);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public synchronized ResponseEntity<?> deleteItem(Long id) throws NotFoundException, ConflictException {
        Optional<Items> items = itemRepository.findById(id);
        if (!items.isPresent())
            throw new NotFoundException(ResponseMessages.NO_ITEMS_FOUND);

        Optional<OrderItems> orderItems = orderItemsRepository.findOneByItemId(id);
        if (orderItems.isPresent())
            throw new ConflictException(ResponseMessages.CANNOT_DELETE);

        itemRepository.deleteById(id);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getAllItems(int pageNo, int pageSize) throws NotFoundException, BadRequestException {

        if (pageNo <= 0 || pageSize < 1)
            throw new BadRequestException(ResponseMessages.PAGINATION_MESSAGE);

        Page<Items> allItems = itemRepository.findAll(PageRequest.of(pageNo - 1, pageSize));

        if (allItems.isEmpty())
            throw new NotFoundException(ResponseMessages.STORE_EMPTY);

        List<Items> itemsList = allItems.getContent().stream().map(items ->
                new Items(items.getId(), items.getName(), items.getItemPrice(), items.getStockCount())).collect(Collectors.toList());

        return new ResponseEntity<>(new AllItemsResponse(itemsList, allItems.getTotalElements()), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<?> getItemDetails(Long id) throws NotFoundException {
        Optional<Items> items = itemRepository.findById(id);
        return new ResponseEntity<>(items.orElseThrow(()
                -> new NotFoundException(ResponseMessages.NO_ITEMS_FOUND)), HttpStatus.OK);
    }
}
