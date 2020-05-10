package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.biskit.entity.OrderItems;
import com.project.biskit.entity.Orders;

import java.util.List;

public class OrderDetailResponse {

    @JsonProperty("order")
    private Orders order;

    @JsonProperty("order_items")
    private List<OrderDetailItems> orderItems;

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public List<OrderDetailItems> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderDetailItems> orderItems) {
        this.orderItems = orderItems;
    }

    public OrderDetailResponse(Orders order, List<OrderDetailItems> orderItems) {
        this.order = order;
        this.orderItems = orderItems;
    }
}
