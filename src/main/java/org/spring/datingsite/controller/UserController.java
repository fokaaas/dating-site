package org.spring.datingsite.controller;

import org.spring.datingsite.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class UserController {

    @GetMapping("/form")
    public String getCreateForm(Model model) {
        model.addAttribute("user", new User());
        return "create-user-form";
    }

    @PostMapping()
    public String create(User user, Model model) {
        model.addAttribute("user", user);
        return "create-user";
    }
}
