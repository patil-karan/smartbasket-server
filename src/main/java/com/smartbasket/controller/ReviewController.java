package com.smartbasket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.smartbasket.entity.Review;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.exception.UserException;
import com.smartbasket.request.ReviewRequest;
import com.smartbasket.service.ReviewService;
import com.smartbasket.service.UserService;

@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private ReviewService reviewService;
	
	@PostMapping("/create")
	public ResponseEntity<Review> CreateReview(@RequestBody ReviewRequest request,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		Review review = reviewService.createReview(request, user);
		
		return new ResponseEntity<Review>(review,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Review>> getProductReview(@PathVariable Long productId) throws UserException,ProductException{
		
		List<Review> reviews = reviewService.getAllReview(productId);
		
		return new ResponseEntity<List<Review>>(reviews,HttpStatus.CREATED);
	}
	
}
