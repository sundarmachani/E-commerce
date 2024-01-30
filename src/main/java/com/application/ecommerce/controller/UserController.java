package com.application.ecommerce.controller;


import com.application.ecommerce.model.custom.Log;
import com.application.ecommerce.model.User;
import com.application.ecommerce.model.custom.Constant;
import com.application.ecommerce.model.custom.CustomErrorResponse;
import com.application.ecommerce.service.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<Object> createUser(@RequestBody @Valid User user) {
        Log.LOGGER.info(Constant.CREATE_USER_REQUEST);
        try {
            User createdUser = userService.createUser(user);
            return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        } catch (Exception e) {
            CustomErrorResponse error = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("getUser")
    public ResponseEntity<Object> getUser(@RequestParam @Email String email) {
        Log.LOGGER.info(Constant.GET_USER_REQUEST + email);
        try {
            User user = userService.getUser(email);
            return new ResponseEntity<>(user, HttpStatus.FOUND);
        } catch (Exception e) {
            CustomErrorResponse error = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Object> updateUser(@RequestBody @Valid User user) {
        Log.LOGGER.info(Constant.UPDATE_USER_REQUEST);
        try {
            User updatedUser = userService.updateUser(user);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception e) {
            CustomErrorResponse errorResponse = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<CustomErrorResponse> deleteUser(@Email String email) {
        Log.LOGGER.info(Constant.DELETE_USER_REQUEST);
        try {
            if (userService.deleteUser(email)) {
                CustomErrorResponse message = new CustomErrorResponse(Constant.USER_DELETED_SUCCESS + email);
                return new ResponseEntity<>(message, HttpStatus.OK);
            } else {
                CustomErrorResponse message = new CustomErrorResponse(Constant.DELETE_USER_ERROR + email);
                return new ResponseEntity<>(message, HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            CustomErrorResponse message = new CustomErrorResponse(e.getMessage());
            return new ResponseEntity<>(message, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
