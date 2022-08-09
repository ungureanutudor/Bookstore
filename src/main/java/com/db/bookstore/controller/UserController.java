package com.db.bookstore.controller;

import com.db.bookstore.model.Book;
import com.db.bookstore.model.User;

import com.db.bookstore.service.UserService;
import com.db.bookstore.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
public class UserController {
    @Autowired
    UserService userService;

    @Autowired
    BookService bookService;

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
        ModelAndView modelAndView = new ModelAndView("redirect:/error");
        return modelAndView;
    }

    @GetMapping("/dashboard")
    public ModelAndView getDashBoard(@CookieValue int id){
        ModelAndView modelAndView=new ModelAndView("dashboard");
        try {
            User userId1 = userService.findUserById(id);
            modelAndView.addObject("user", userId1);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        try {
            List<Book> books = bookService.listAll();
            modelAndView.addObject("bookList",books);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return modelAndView;
    }

    @GetMapping("/add-books")
    public ModelAndView bookForm(Book book, @CookieValue int id) {
            if(userService.verifyStatus(id) == true) {
                ModelAndView modelAndView = new ModelAndView("add-books");
                return modelAndView;
            } else {
                ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
                return modelAndView;
            }

    }

    @PostMapping("/add-books")
    public ModelAndView addSomeBooks(Book book, @CookieValue int id){
        if(userService.verifyStatus(id) == true) {
            bookService.insertBooks(book);
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/dashboard");
            return modelAndView;
        }


    }
}
