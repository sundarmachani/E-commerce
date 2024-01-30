package com.application.ecommerce.service;

import com.application.ecommerce.model.eNum.OrderStatusEnum;
import com.application.ecommerce.model.order.Order;
import com.application.ecommerce.model.order.PatchOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface OrderService {

    public Order createorder(Order order);

    public Order getOrder(String orderId);

    public List<Order> getOrdersByEmail(String email);

    public HashMap<String, Order> getAllOrdersWithFuzzySearch(String orderStatus);

    public Order patchOrder(PatchOrder patchOrder);

}
