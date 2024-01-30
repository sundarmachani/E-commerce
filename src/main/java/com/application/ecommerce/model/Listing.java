package com.application.ecommerce.model;//package com.application.ecommerce.model;

import com.application.ecommerce.model.eNum.ProductTypeEnum;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Listing {

    @Id
    private String listingId;

    @NonNull
    private String title;

    @NonNull
    private String description;

    private double price;

    @NonNull
    private ProductTypeEnum type;

}
