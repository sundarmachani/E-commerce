package com.application.ecommerce.service.impl;

import com.application.ecommerce.model.cart.CartVM;
import com.application.ecommerce.model.eNum.OrderStatusEnum;
import com.application.ecommerce.model.order.Order;
import com.application.ecommerce.model.order.PatchOrder;
import com.application.ecommerce.repository.OrderRepository;
import com.application.ecommerce.service.CartService;
import com.application.ecommerce.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private CartService cartService;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public Order createorder(Order order) {
        CartVM cartVM = cartService.getCart(order.getUserEmail());
        if (cartVM != null) {
            order.setOrderId(orderIdGenerator());
            order.setListingDetails(cartVM.getListings());
            order.setPlacedAt(new Date());
            order.setOrderStatus(OrderStatusEnum.PENDING);
            order.setTax(cartVM.getTax());
            order.setSubTotal(cartVM.getSubTotal());
            order.setOrderTotal(cartVM.getTotal());
            if (!cartService.clearCart(order.getUserEmail())) {
                throw new RuntimeException("Not able to place order as cart can't be cleared");
            }
            return orderRepository.insert(order);
        } else {
            return null;
        }
    }

    @Override
    public Order getOrder(String orderId) {
        return orderRepository.findById(orderId).orElse(null);
    }

    @Override
    public List<Order> getOrdersByEmail(String email) {
        List<Order> orderList = getAllOrders();
        return orderList.stream().filter(o -> o.getUserEmail().equalsIgnoreCase(email)).toList();
    }

    @Override
    public HashMap<String, Order> getAllOrdersWithFuzzySearch(String orderStatus) {
        List<Order> orderList = getAllOrders();
        HashMap<String, Order> orderHashMap = new HashMap<>();
        if (orderStatus == null) {
            orderList.forEach(order -> orderHashMap.put(order.getOrderId(), order));
        } else {
            orderList.stream().filter(order -> order.getOrderStatus().name().equalsIgnoreCase(orderStatus))
                    .forEach(order -> orderHashMap.put(order.getOrderId(), order));
        }
        return orderHashMap;
    }


    private List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order patchOrder(PatchOrder patchOrder) {
        Order order = getOrder(patchOrder.getOrderId());
        if (order != null) {
            if (order.getOrderStatus().equals(patchOrder.getOrderStatus())) {
                throw new RuntimeException("Cannot patch order due to same orderStatus");
            } else {
                order.setOrderStatus(patchOrder.getOrderStatus());
                order.setUpdatedAt(new Date());
                return orderRepository.save(order);
            }
        } else {
            return null;
        }
    }

    private String orderIdGenerator() {
        String INCLUDE_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        int length = 5;
        Random random = new Random();
        StringBuilder stringBuilder = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int randomIndex = random.nextInt(INCLUDE_CHARS.length());
            char randomChar = INCLUDE_CHARS.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return "OD" + stringBuilder;
    }
}
