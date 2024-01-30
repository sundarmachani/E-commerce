package com.application.ecommerce.model.order;

import com.application.ecommerce.model.eNum.OrderStatusEnum;
import lombok.Data;
import lombok.NonNull;

@Data
public class PatchOrder {

    @NonNull
    private String orderId;

    @NonNull
    private OrderStatusEnum orderStatus;
}
