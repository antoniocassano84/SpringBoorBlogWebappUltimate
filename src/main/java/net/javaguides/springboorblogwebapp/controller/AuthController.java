package net.javaguides.springboorblogwebapp.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import net.javaguides.springboorblogwebapp.dto.RegistrationDto;
import net.javaguides.springboorblogwebapp.entity.User;
import net.javaguides.springboorblogwebapp.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@AllArgsConstructor
public class AuthController {

    UserService userService;

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new RegistrationDto());
        return "register";
    }

    @PostMapping("/register/save")
    public String register(@ModelAttribute("user") @Valid RegistrationDto registrationDto,
                           BindingResult bindingResult, Model model) {
        Optional<User> existingUser = userService.findByEmail(registrationDto.getEmail());
        if(existingUser.isPresent()) {
            bindingResult.rejectValue("email", "exists",
                    "There is already an user with same email");
        }
        if(bindingResult.hasErrors()) {
            model.addAttribute("user", registrationDto);
            return "register";
        }
        userService.saveUser(registrationDto);
        return "redirect:/register?success";
    }
}
