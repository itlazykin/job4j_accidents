package ru.job4j.accidents.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.accidents.model.User;
import ru.job4j.accidents.service.UserService;

@Controller
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/login")
    public String loginPage(@RequestParam(value = "error", required = false) String error,
                            @RequestParam(value = "logout", required = false) String logout,
                            Model model) {
        String errorMessage = null;
        if (error != null) {
            errorMessage = "Username or Password is incorrect !!";
        }
        if (logout != null) {
            errorMessage = "You have been successfully logged out !!";
        }
        model.addAttribute("errorMessage", errorMessage);
        return "login";
    }

    @GetMapping("/logout")
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/login?logout=true";
    }

    @PostMapping("/reg")
    public String regSave(Model model, @ModelAttribute User user) {
        var savedUser = userService.save(user);
        if (savedUser.isEmpty()) {
            model.addAttribute("errorMessage", "The user already exists");
            return "reg";
        }
        return "redirect:/login";
    }

    @GetMapping("/reg")
    public String regPage() {
        return "reg";
    }
}