package com.project.biskit.service;

import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.AddItemRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    ResponseEntity<?> addItem(List<AddItemRequest> addItems) throws BadRequestException;

    ResponseEntity<?> updateItem(Long id, AddItemRequest updateItem) throws NotFoundException;

    ResponseEntity<?> deleteItem(Long id) throws NotFoundException, ConflictException;

    ResponseEntity<?> getAllItems(int pageNo, int pageSize) throws NotFoundException;

    ResponseEntity<?> getItemDetails(Long id) throws NotFoundException;
}
