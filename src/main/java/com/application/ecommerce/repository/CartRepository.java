package com.application.ecommerce.repository;

import com.application.ecommerce.model.cart.CartVM;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CartRepository extends MongoRepository<CartVM, String> {
}
