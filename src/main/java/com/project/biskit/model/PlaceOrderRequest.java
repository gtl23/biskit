package com.project.biskit.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlaceOrderRequest {

    @JsonProperty("item_id")
    private Long itemId;

    @JsonProperty("count")
    private Long count;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
