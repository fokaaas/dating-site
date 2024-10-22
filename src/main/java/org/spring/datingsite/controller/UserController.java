package org.spring.datingsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(Model model, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", userService.getAllUsers(currentUser.getId()));
        return "general";
    }

    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") String userId, Model model) {
        UserEntity user = userService.getUser(userId);
        model.addAttribute("user", user);
        return "user";
    }
}
