package com.smartbasket.service;

import java.util.List;

import com.smartbasket.entity.Rating;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.request.RatingRequest;

public interface RatingService {

	public Rating createRating(RatingRequest req, User user)throws ProductException;
	
	public List<Rating> getProductRating(Long productId);
}
