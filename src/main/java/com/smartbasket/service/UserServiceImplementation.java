package com.smartbasket.service;


import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartbasket.config.JwtProvider;
import com.smartbasket.entity.User;
import com.smartbasket.exception.UserException;
import com.smartbasket.repository.UserRepository;



@Service
public class UserServiceImplementation implements UserService{
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	
	public UserServiceImplementation(UserRepository userRepository, JwtProvider jwtProvider) {
		this.userRepository = userRepository;
		this.jwtProvider = jwtProvider;
	}

	@Override
	public User findById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		
		if (user.isPresent()) {
			return user.get();
		}
		throw new UserException("User Not Found With Id -"+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromToken(jwt);
		
		User user = userRepository.findByEmail(email);
		
		if (user == null) {
			throw new UserException("User Not Found With This Exceptio"+email);
		}
		return user;
	}

}
