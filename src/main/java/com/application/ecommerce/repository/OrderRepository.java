package com.application.ecommerce.repository;//package com.application.ecommerce.repository;

import com.application.ecommerce.model.order.Order;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface OrderRepository extends MongoRepository<Order, String> {
}
