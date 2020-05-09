package com.project.biskit.service;

import com.project.biskit.model.AddItemRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface ItemService {
    ResponseEntity<?> addItem(List<AddItemRequest> addItems);
}
