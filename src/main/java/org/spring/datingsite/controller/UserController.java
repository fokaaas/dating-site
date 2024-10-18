package org.spring.datingsite.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.service.UserService;
import org.spring.datingsite.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getCreateForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "registration-form";
    }

    @PostMapping("/success")
    public String register(UserEntity user, Model model, HttpServletResponse response) {
        model.addAttribute("user", user);
        String token = userService.registerUser(user);
        CookieUtil.setAuthCookie(token, response);
        return "registration-success";
    }
}
