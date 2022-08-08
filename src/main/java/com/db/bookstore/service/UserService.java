package com.db.bookstore.service;

import com.db.bookstore.model.User;
import com.db.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void insertUser(User user){
        userRepository.save(user);
    }
}
