package com.application.ecommerce.service;


import com.application.ecommerce.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    public User createUser(User user);

    public User getUser(String email);

    public User updateUser(User user);

    public boolean deleteUser(String email);

}
