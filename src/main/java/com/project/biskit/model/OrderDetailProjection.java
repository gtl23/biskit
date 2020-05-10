package com.project.biskit.model;

import com.project.biskit.utils.Status;

public interface OrderDetailProjection {

    Status getItemStatus();

    Long getOrderItemId();

    Long getItemId();

    Long getCount();

    Double getAmount();

    String getName();

}
