package com.smartbasket.service;

import com.smartbasket.entity.User;
import com.smartbasket.exception.UserException;

public interface UserService {

public User findById(Long userId) throws UserException;
	
	public User findUserProfileByJwt(String jwt) throws UserException;
}
