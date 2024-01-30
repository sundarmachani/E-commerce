package com.application.ecommerce.model.cart;

import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class Cart {

    @Email
    @NonNull
    private String email;

    @NonNull
    private List<String> listingIds;

}
