package com.coderscampus.brewDayApp.web;

import com.coderscampus.brewDayApp.domain.User;
import com.coderscampus.brewDayApp.service.UserServiceImpl;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class UserController {

    private final UserServiceImpl userService;


    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }



    @GetMapping("/home/{userId}")
    public String getUserHomePage(@PathVariable Integer userId, ModelMap model, HttpSession httpSession) {
        User user = userService.findUserById(userId).orElse(null);
        if(user == null) {
            return "redirect:/signin";
        }
        httpSession.setAttribute("user", user);
        model.put("user", user);
        return "user/home";
    }
}
