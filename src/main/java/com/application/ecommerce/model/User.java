package com.application.ecommerce.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.*;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class User {

    @NonNull
    private String name;

    @Id
    @NonNull
    @Email(message = "Invalid email address")
    private String email;

    @Max(value = 100, message = "Age cannot be more than 100 years, change the dateOfBirth")
    @Positive(message = "Age cannot be negative")
    private int age;

    @NonNull
    private String phone;

    @NonNull
    private String address;

    @NonNull
    private String state;

    @NonNull
    @Min(value = 5, message = "pincode must be minimum 5 character length")
    private String pincode;

    @NonNull
    private String country;

}
