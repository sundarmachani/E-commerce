package com.application.ecommerce.service.impl;

import com.application.ecommerce.model.cart.Cart;
import com.application.ecommerce.model.cart.CartVM;
import com.application.ecommerce.model.Listing;
import com.application.ecommerce.repository.CartRepository;
import com.application.ecommerce.service.CartService;
import com.application.ecommerce.service.ListingService;
import com.application.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private UserService userService;

    @Autowired
    private ListingService listingService;

    @Autowired
    private CartRepository cartRepository;

    @Override
    public CartVM addToCart(Cart cart) {
        userService.getUser(cart.getEmail());
        Optional<CartVM> optionalCartVM = cartRepository.findById(cart.getEmail());
        if (optionalCartVM.isPresent()) {
            CartVM dbcartVM = optionalCartVM.get();
            cart.getListingIds().forEach(id -> {
                Listing listing = listingService.getListing(id);
                dbcartVM.getListings().add(listing);
            });
            dbcartVM.setUpdatedAt(LocalDateTime.now(ZoneOffset.UTC));
            return cartRepository.save(dbcartVM);
        } else {
            List<Listing> cartListings = new ArrayList<>();
            cart.getListingIds().forEach(id -> {
                Listing listing = listingService.getListing(id);
                cartListings.add(listing);
            });
            CartVM finalCartVM = CartVM.builder().email(cart.getEmail())
                    .addedAt(LocalDateTime.now(ZoneOffset.UTC)).listings(cartListings).build();
            return cartRepository.save(finalCartVM);
        }
    }


    @Override
    public CartVM getCart(String email) {
        userService.getUser(email);
        Optional<CartVM> cartVM = cartRepository.findById(email);
        if (cartVM.isPresent()){
            CartVM finalCartVM = cartVM.get();
            priceCalculator(finalCartVM);
            return finalCartVM;
        } else {
            return null;
        }
    }

    private static void priceCalculator(CartVM cartVM) {
        double subTotal = cartVM.getListings().stream().mapToDouble(Listing::getPrice).sum();
//      taxRate = 7.5 %
        double tax = subTotal * 0.075;
        double total = subTotal + tax;
        cartVM.setSubTotal(subTotal);
        cartVM.setTax(tax);
        cartVM.setTotal(total);
    }

//    @Override
//    public CartVM updateCart(CartVM cartVM) {
//        userService.getUser(cartVM.getEmail());
//
//    }

    @Override
    public CartVM deleteItemFromCart(Cart cart) {
        CartVM dbCartVM = getCart(cart.getEmail());
        for (String id : cart.getListingIds()) {
            boolean isDeleted = dbCartVM.getListings().removeIf(list -> list.getListingId().equalsIgnoreCase(id));
            if (isDeleted) {
                priceCalculator(dbCartVM);
                return cartRepository.save(dbCartVM);
            } else {
                return null;
            }
        }
        return null;
    }

    @Override
    public boolean clearCart(String email) {
        CartVM cartVM = getCart(email);
        if (cartVM != null) {
            cartRepository.deleteById(email);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<CartVM> getAllCarts() {
        return cartRepository.findAll();
    }
}
