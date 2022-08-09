package com.db.bookstore.service;

import com.db.bookstore.model.User;
import com.db.bookstore.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    public void insertUser(User user){
        user.setRole("client");
        userRepository.save(user);
    }
    public User findByUsernameOrEmailAndPassword(User user) throws Exception {
       List<User> users = userRepository.findByUsernameOrEmailAndPassword(user.getUsername(), user.getEmail(), user.getPassword());

       if(users.size() == 0)
       {
           throw new Exception("No user found");
       }

       if(users.size() == 1)
       {
           return users.get(0);
       }

       if(users.size() > 1)
       {
           throw new Exception("Database error");
       }

        return null;
    }

    public User findUserById(int id) {
        User user1 = userRepository.findUserById(id);
        return user1;
    }

    public Boolean verifyStatus(int id) {
        User user1 = userRepository.findUserById(id);
        if (user1.getRole() == "admin") {
            return true;
        } else return false;
    }

}
