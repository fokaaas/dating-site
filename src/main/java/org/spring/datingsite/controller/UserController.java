package org.spring.datingsite.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.datingsite.entity.UserEntity;
import org.spring.datingsite.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

        if (currentUser == null) {
            return "redirect:/login";
        }

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("inviters", userService.getInviters(currentUser.getId()));

        List<UserEntity> users;
        if ("Male".equalsIgnoreCase(currentUser.getSex())) {
            users = userService.getWomen();
        } else if ("Female".equalsIgnoreCase(currentUser.getSex())) {
            users = userService.getMen();
        } else {
            users = userService.getMen();
            users.addAll(userService.getWomen());
        }
        model.addAttribute("users", users);
        return "general";
    }

    @GetMapping("/search")
    public String searchUsers(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) Integer minAge,
            @RequestParam(required = false) Integer maxAge,
            @RequestParam(required = false) String location,
            Model model,
            HttpServletRequest request
    ) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        List<UserEntity> users = userService.searchUsers(keyword, minAge, maxAge, location);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);

        return "general";
    }



    @GetMapping("/profile/edit")
    public String getProfile(Model model, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");

        if (currentUser == null) {
            return "redirect:/login";
        }

        String formattedBirthDate = currentUser.getBirthDate() != null
                ? currentUser.getBirthDate().toString()
                : "";

        model.addAttribute("user", currentUser);
        model.addAttribute("formattedBirthDate", formattedBirthDate); // add formatted date

        return "profile";
    }

    @PostMapping("/profile/edit")
    public String updateProfile(@ModelAttribute UserEntity user, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");

        if (currentUser != null) {
            currentUser.setFirstName(user.getFirstName());
            currentUser.setMiddleName(user.getMiddleName());
            currentUser.setLastName(user.getLastName());
            currentUser.setSex(user.getSex());
            currentUser.setPhoto(user.getPhoto());
            currentUser.setEmail(user.getEmail());
            currentUser.setPhoneNumber(user.getPhoneNumber());
            currentUser.setBirthDate(user.getBirthDate());
            currentUser.setResidence(user.getResidence());
            currentUser.setAboutMe(user.getAboutMe());

            userService.updateUser(currentUser); // Save updated user
        }

        return "redirect:/users/profile/edit"; // Redirect back to profile page
    }

    @GetMapping("/{id}")
    public String getUserProfile(@PathVariable("id") String userId, Model model, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        model.addAttribute("invitationState", userService.getInvitationState(currentUser.getId(), userId));
        model.addAttribute("user", userService.getUser(userId));
        return "user";
    }

    @PostMapping("/{id}/invitations")
    public String inviteUser(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.inviteUser(currentUser.getId(), userId);
        return "redirect:/users/" + userId;
    }

    @PostMapping("/{id}/invitations-accept")
    public String acceptInvitation(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.acceptInvitation(userId, currentUser.getId());
        return "redirect:/users";
    }

    @PostMapping("/{id}/invitations-reject")
    public String rejectInvitation(@PathVariable("id") String userId, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.rejectInvitation(currentUser.getId(), userId);
        return "redirect:/users/" + userId;
    }
}
