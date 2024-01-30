package com.application.ecommerce.repository;//package com.application.ecommerce.repository;

import com.application.ecommerce.model.Listing;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ListingRepository extends MongoRepository<Listing, String> {
}
