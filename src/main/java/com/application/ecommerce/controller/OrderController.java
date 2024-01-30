package com.application.ecommerce.controller;

import com.application.ecommerce.model.custom.Constant;
import com.application.ecommerce.model.custom.CustomErrorResponse;
import com.application.ecommerce.model.custom.Log;
import com.application.ecommerce.model.eNum.OrderStatusEnum;
import com.application.ecommerce.model.order.Order;
import com.application.ecommerce.model.order.PatchOrder;
import com.application.ecommerce.service.OrderService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<Object> createOrder(@RequestBody @Valid Order order) {
        Log.LOGGER.info(Constant.CREATE_ORDER_REQUEST);
        try {
            Order createdOrder = orderService.createorder(order);
            if (createdOrder != null) {
                return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.CREATE_ORDER_ERROR + order.getUserEmail());
                return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOrder")
    public ResponseEntity<Object> getOrder(@RequestParam String orderId) {
        Log.LOGGER.info(Constant.GET_ORDER_REQUEST);
        try {
            Order order = orderService.getOrder(orderId);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.FOUND);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.GET_ORDER_ERROR + orderId);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/getOrdersByEmail")
    public ResponseEntity<Object> getOrdersByEmail(@RequestParam @Email String email) {
        Log.LOGGER.info(Constant.GET_ORDERS_BY_EMAIL_REQUEST);
        try {
            List<Order> orderList = orderService.getOrdersByEmail(email);
            if (!orderList.isEmpty()) {
                return new ResponseEntity<>(orderList, HttpStatus.FOUND);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.GET_ORDERS_BY_EMAIL_ERROR + email);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/patch")
    public ResponseEntity<Object> patchOrder(@RequestParam PatchOrder patchOrder) {
        Log.LOGGER.info(Constant.PATCH_ORDER_REQUEST);
        try {
            Order order = orderService.patchOrder(patchOrder);
            if (order != null) {
                return new ResponseEntity<>(order, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.PATCH_ORDER_ERROR + patchOrder.getOrderId());
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/allFuzzy")
    public ResponseEntity<Object> getAllOrdersWithFuzzySearch(@RequestParam(required = false) String orderStatus) {
        Log.LOGGER.info(Constant.ALL_ORDER_REQUEST);
        try {
            if (orderStatus != null && !Arrays.stream((OrderStatusEnum.values()))
                    .map(String::valueOf).toList().contains(orderStatus.toUpperCase())){
                return new ResponseEntity<>(new CustomErrorResponse(Constant.ALL_ORDER_INVALID_ENUM), HttpStatus.BAD_REQUEST);
            }
            Map<String, Order> orderHashMap = orderService.getAllOrdersWithFuzzySearch(orderStatus);
            if (!orderHashMap.isEmpty()) {
                return new ResponseEntity<>(orderHashMap, HttpStatus.FOUND);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.ALL_ORDER_ERROR);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
