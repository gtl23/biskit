package com.project.biskit.controller;

import com.project.biskit.model.AddItemRequest;
import com.project.biskit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ItemService itemService;

    @PostMapping("/add_item")
    public ResponseEntity<?> addItem(@RequestBody List<AddItemRequest> addItems){
        return itemService.addItem(addItems);
    }

}
