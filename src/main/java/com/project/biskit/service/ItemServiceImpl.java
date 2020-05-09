package com.project.biskit.service;

import com.project.biskit.entity.Items;
import com.project.biskit.model.AddItemRequest;
import com.project.biskit.repository.ItemRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    ItemRepository itemRepository;

    @Override
    public ResponseEntity<?> addItem(List<AddItemRequest> addItems) {
        List<Items> items = addItems.stream().map(item ->
                new Items(item.getName(), item.getItemPrice(), item.getStockCount())).collect(Collectors.toList());
        itemRepository.saveAll(items);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
