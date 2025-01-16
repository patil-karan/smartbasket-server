package com.smartbasket.service;

import java.util.List;

import com.smartbasket.entity.Review;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.request.ReviewRequest;

public interface ReviewService {

	public Review createReview(ReviewRequest req,User user)throws ProductException; 
	
	public List<Review> getAllReview(Long productId);
}
