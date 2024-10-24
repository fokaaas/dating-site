package org.spring.datingsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        model.addAttribute("inviters", userService.getInviters(currentUser.getId()));
        model.addAttribute("users", userService.getAllUsers(currentUser.getId()));
        return "general";
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable("id") String userId, Model model, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        model.addAttribute("invitationState", userService.getInvitationState(currentUser.getId(), userId));
        model.addAttribute("user", userService.getUser(userId));
        return "user";
    }

    @PostMapping("/{id}/invitations")
    public void inviteUser(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.inviteUser(currentUser.getId(), userId);
    }

    @PatchMapping("/{id}/invitations")
    public void acceptInvitation(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.acceptInvitation(currentUser.getId(), userId);
    }

    @DeleteMapping("/{id}/invitations")
    public void rejectInvitation(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.rejectInvitation(currentUser.getId(), userId);
    }
}
