package com.application.ecommerce.service;//package com.application.ecommerce.service;

import com.application.ecommerce.model.cart.Cart;
import com.application.ecommerce.model.cart.CartVM;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CartService {

    public CartVM addToCart(Cart cart);

    public CartVM getCart(String email);

//    CartVM updateCart(CartVM cartVM);

    public CartVM deleteItemFromCart(Cart cart);

    public boolean clearCart(String email);

    public List<CartVM> getAllCarts();

}
