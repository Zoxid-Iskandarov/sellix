package com.walking.sellix.controller;

import com.walking.sellix.model.user.request.CreateUserRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class AuthController {

    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("userDto") CreateUserRequest createUserRequest) {
        return "auth/registration";
    }
}
