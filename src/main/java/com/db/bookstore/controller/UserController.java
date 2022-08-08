package com.db.bookstore.controller;

import com.db.bookstore.model.User;

import com.db.bookstore.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping("/register")
    public ModelAndView getRegisterForm(){
        ModelAndView modelAndView = new ModelAndView("register-form");
        return modelAndView;
    }

    @PostMapping("/register")
    public ModelAndView addUser(User user){
        userService.insertUser(user);
        ModelAndView modelAndView = new ModelAndView("redirect:/login");
        return modelAndView;
    }

    @GetMapping("/login")
    public ModelAndView getLoginForm(){
        ModelAndView modelAndView = new ModelAndView("login-form");
        return modelAndView;
    }

    @PostMapping("/login")
    public ModelAndView verifyUser(User user, HttpServletResponse response){
        try {
            User user1 = userService.findByUsernameOrEmailAndPassword(user);
            response.addCookie(new Cookie("id", "" + user1.getId()));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");

        return modelAndView;



    }

    @GetMapping("/dashboard")
    public ModelAndView getDashBoard(){
        ModelAndView modelAndView=new ModelAndView("dashboard");
        modelAndView.addObject("bookList","");
        return modelAndView;
    }



}
