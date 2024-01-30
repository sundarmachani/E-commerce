package com.application.ecommerce.model.order;//package com.application.ecommerce.model;
import com.application.ecommerce.model.Listing;
import com.application.ecommerce.model.eNum.OrderStatusEnum;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Null;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;
import org.springframework.data.annotation.Id;

import java.util.Date;
import java.util.List;

@Data
@Builder
public class Order {

    @Id
    @Null(message = "orderId not a user input")
    private String orderId;

    private Date placedAt;

    private Date updatedAt;

    @NonNull
    @Email
    private String userEmail;

    @Null(message = "listingDetails not a user input")
    private List<Listing> listingDetails;

    private double subTotal;

    private double tax;

    private double OrderTotal;

    @Null(message = "orderType not a user input")
    private OrderStatusEnum orderStatus;

}
