package com.application.ecommerce.controller;

import com.application.ecommerce.model.cart.Cart;
import com.application.ecommerce.model.cart.CartVM;
import com.application.ecommerce.model.custom.Constant;
import com.application.ecommerce.model.custom.CustomErrorResponse;
import com.application.ecommerce.model.custom.Log;
import com.application.ecommerce.service.CartService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("api/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @PostMapping("/addToCart")
    public ResponseEntity<Object> addToCart(@RequestBody @Valid Cart cart) {
        Log.LOGGER.info(Constant.ADD_TO_CART_REQUEST);
        try {
            CartVM cartVM = cartService.addToCart(cart);
            return new ResponseEntity<>(cartVM, HttpStatus.CREATED);
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/getCart")
    public ResponseEntity<Object> getCart(@RequestParam @Email String email) {
        Log.LOGGER.info(Constant.GET_CART_REQUEST);
        try {
            CartVM cartVM = cartService.getCart(email);
            if (cartVM != null) {
                return new ResponseEntity<>(cartVM, HttpStatus.FOUND);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.CART_NOT_FOUND + email);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Object> deleteItemFromCart(@RequestBody @Valid Cart cart) {
        Log.LOGGER.info(Constant.DELETE_ITEM_REQUEST);
        try {
            CartVM cartVM = cartService.deleteItemFromCart(cart);
            if (cartVM != null) {
                return new ResponseEntity<>(cartVM, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.DELETE_CART_ERROR);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/clear")
    public ResponseEntity<Object> clearCart(@RequestParam @Email String email) {
        Log.LOGGER.info(Constant.CLEAR_CART_REQUEST);
        try {
            if (cartService.clearCart(email)) {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.CLEAR_CART_SUCCESS + email);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.CLEAR_CART_FAILED + email);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }

        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllCarts() {
        Log.LOGGER.info(Constant.GET_ALL_CARTS_REQUEST);
        try {
            List<CartVM> cartVMList = cartService.getAllCarts();
            if (!cartVMList.isEmpty()) {
                return new ResponseEntity<>(cartVMList, HttpStatus.OK);
            } else {
                CustomErrorResponse customErrorResponse = new CustomErrorResponse(Constant.NO_CARTS);
                return new ResponseEntity<>(customErrorResponse, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            CustomErrorResponse customErrorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
