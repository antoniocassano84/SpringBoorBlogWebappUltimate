package net.javaguides.springboorblogwebapp.service;

import java.util.Optional;

import net.javaguides.springboorblogwebapp.dto.RegistrationDto;
import net.javaguides.springboorblogwebapp.entity.User;

public interface UserService {
    void saveUser(RegistrationDto registrationDto);
    Optional<User> findByEmail(String email);
}
