package com.project.biskit.service;

import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.PlaceOrderRequest;
import com.project.biskit.security.CustomUserDetail;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CustomerService {
    ResponseEntity<?> getAllItems(int pageNo, int pageSize) throws NotFoundException;

    ResponseEntity<?> getItemDetails(Long itemId) throws NotFoundException;

    ResponseEntity<?> placeOrder(CustomUserDetail userDetail, List<PlaceOrderRequest> orderRequestList)
            throws BadRequestException, ConflictException;

    ResponseEntity<?> cancelOrder(Long orderId) throws NotFoundException, BadRequestException;
}
