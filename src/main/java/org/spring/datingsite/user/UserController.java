package org.spring.datingsite.user;

import jakarta.servlet.http.HttpServletRequest;
import org.spring.datingsite.user.entity.SearchEntity;
import org.spring.datingsite.user.entity.UserEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String getUsers(
            @ModelAttribute SearchEntity search,
            Model model,
            HttpServletRequest request
    ) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        List<UserEntity> users = userService.getUsers(currentUser.getId(), search);
        List<UserEntity> inviters = userService.getInviters(currentUser.getId());

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("users", users);
        model.addAttribute("inviters", inviters);
        model.addAttribute("search", search);

        return "general";
    }



    @GetMapping("/edit")
    public String getProfile(Model model, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        String formattedBirthDate = currentUser.getBirthDate() != null
                ? currentUser.getBirthDate().toString()
                : "";
        model.addAttribute("user", currentUser);
        model.addAttribute("formattedBirthDate", formattedBirthDate);

        return "user-edit";
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute UserEntity user, HttpServletRequest request) {
        UserEntity currentUser = (UserEntity) request.getAttribute("currentUser");
        userService.updateUser(currentUser, user);
        return "redirect:/users";
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
