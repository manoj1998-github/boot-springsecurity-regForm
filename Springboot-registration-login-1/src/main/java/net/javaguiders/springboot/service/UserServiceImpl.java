package net.javaguiders.springboot.service;

import java.util.Arrays;

import java.util.Collection;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.javaguiders.springboot.model.Role;
import net.javaguiders.springboot.model.User;
import net.javaguiders.springboot.repository.UserRepository;
import net.javaguiders.springboot.web.dto.UserRegistrationDto;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repo;

	@Override
	public User save(UserRegistrationDto registrationdto) {
		User user = new User(registrationdto.getFirstName(), registrationdto.getLastName(), registrationdto.getEmail(),
				registrationdto.getPassword(), Arrays.asList(new Role("ROLE_USER")));
		return repo.save(user);
	}

	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = repo.findByEmail(username);
		if (user == null) {
			throw new UsernameNotFoundException("invalid user name or password");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				mapRolesToAuthorities(user.getRoles()));
	}

	private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
		return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());

	}
}
