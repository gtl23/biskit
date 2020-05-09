package com.project.biskit.controller;

import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.AddItemRequest;
import com.project.biskit.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    ItemService itemService;

    @PostMapping("/add_item")
    public ResponseEntity<?> addItem(@RequestBody List<AddItemRequest> addItems) throws BadRequestException {
        return itemService.addItem(addItems);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateItem(@PathVariable Long id, @RequestBody AddItemRequest updateItem)
            throws NotFoundException {
        return itemService.updateItem(id, updateItem);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteItem(@PathVariable Long id) throws NotFoundException, ConflictException {
        return itemService.deleteItem(id);
    }

    @GetMapping("/item")
    public ResponseEntity<?> getAllItems(@RequestParam int pageNo, @RequestParam int pageSize) throws NotFoundException {
        return itemService.getAllItems(pageNo, pageSize);
    }

    @GetMapping("/item/{id}")
    public ResponseEntity<?> getItemDetails(@PathVariable Long id) throws NotFoundException{
        return itemService.getItemDetails(id);
    }

}
