package com.application.ecommerce.service.impl;


import com.application.ecommerce.model.User;
import com.application.ecommerce.repository.UserRepository;
import com.application.ecommerce.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User createUser(User user) {
        isPhoneAndPincodeValid(user);
        if (userRepository.existsById(user.getEmail())) {
            throw new RuntimeException("User already exists with email '" + user.getEmail() + "'");
        }
        return userRepository.insert(user);
    }

    private void isPhoneAndPincodeValid(User user) {
        if (!user.getPhone().matches("[0-9]{1,10}")) {
            throw new RuntimeException("Phone number is not in proper format");
        }
        if (!user.getPincode().matches("[0-9]{1,6}")) {
            throw new RuntimeException("Pincode is not valid");
        }
    }

    @Override
    public User getUser(String email) {
        Optional<User> user = userRepository.findById(email);
        if (user.isPresent()) {
            return user.get();
        } else {
            throw new RuntimeException("No user found with '" + email + "'");
        }
    }

    @Override
    public User updateUser(User user) {
        if (userRepository.existsById(user.getEmail())) {
            isPhoneAndPincodeValid(user);
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found for updating");
        }
    }

    @Override
    public boolean deleteUser(String email) {
        if(userRepository.existsById(email)){
            userRepository.deleteById(email);
            return true;
        } else {
            return false;
        }
    }
}
