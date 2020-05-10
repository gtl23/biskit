package com.project.biskit.controller;

import com.project.biskit.exceptions.BadRequestException;
import com.project.biskit.exceptions.ConflictException;
import com.project.biskit.exceptions.NotFoundException;
import com.project.biskit.model.PlaceOrderRequest;
import com.project.biskit.security.CustomUserDetail;
import com.project.biskit.service.CustomerService;
import com.project.biskit.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/all_items")
    public ResponseEntity<?> getAllItems(@RequestParam int pageNo, @RequestParam int pageSize) throws NotFoundException {
        return customerService.getAllItems(pageNo, pageSize);
    }

    @GetMapping("/item/{itemId}")
    public ResponseEntity<?> getItemDetails(@PathVariable Long itemId) throws NotFoundException {
        return customerService.getItemDetails(itemId);
    }

    @PostMapping("/order")
    public ResponseEntity<?> placeOrder(@AuthenticationPrincipal CustomUserDetail userDetail,
                                        @RequestBody List<PlaceOrderRequest> orderRequestList)
            throws BadRequestException, ConflictException {
        return customerService.placeOrder(userDetail, orderRequestList);
    }

}
