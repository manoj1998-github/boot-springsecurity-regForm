package net.javaguiders.springboot.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import net.javaguiders.springboot.model.User;
import net.javaguiders.springboot.web.dto.UserRegistrationDto;

public interface UserService extends UserDetailsService {

	User save(UserRegistrationDto registrationdto);
}
