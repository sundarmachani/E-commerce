package com.application.ecommerce.model.cart;

import com.application.ecommerce.model.Listing;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;


@Document(collection = "cart")
@Data
@Builder
public class CartVM {

    @Id
    private String email;

    private LocalDateTime addedAt;

    private LocalDateTime updatedAt;

    private List<Listing> listings;

    private Double subTotal;

    private Double tax;

    private Double total;

}
