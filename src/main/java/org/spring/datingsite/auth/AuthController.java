package org.spring.datingsite.auth;

import jakarta.servlet.http.HttpServletResponse;
import org.spring.datingsite.user.entity.UserEntity;
import org.spring.datingsite.user.UserService;
import org.spring.datingsite.util.CookieUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping()
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String getCreateForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "registration-form";
    }

    @PostMapping("/submit-registration")
    public String register(UserEntity user, Model model, HttpServletResponse response) {
        model.addAttribute("user", user);
        String token = userService.createUser(user);
        CookieUtil.setAuthCookie(token, response);
        return "registration-success";
    }

    @GetMapping("/login")
    public String getLoginForm(Model model) {
        model.addAttribute("user", new UserEntity());
        return "login-form";
    }

    @PostMapping("/submit-login")
    public String login(UserEntity user, Model model, HttpServletResponse response) {
        model.addAttribute("user", user);
        String token = userService.login(user);
        CookieUtil.setAuthCookie(token, response);
        return "redirect:/users";
    }
}
