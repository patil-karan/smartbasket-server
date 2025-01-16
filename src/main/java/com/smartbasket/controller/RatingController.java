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

import com.smartbasket.entity.Rating;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.exception.UserException;
import com.smartbasket.request.RatingRequest;
import com.smartbasket.service.RatingService;
import com.smartbasket.service.UserService;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private RatingService ratingService;
	
	@PostMapping("/create")
	public ResponseEntity<Rating> createRating(@RequestBody RatingRequest ratingRequest,@RequestHeader("Authorization") String jwt)throws UserException,ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		
		Rating rating = ratingService.createRating(ratingRequest, user);
		
		return new ResponseEntity<Rating>(rating,HttpStatus.CREATED);
	}
	
	@GetMapping("/product/{productId}")
	public ResponseEntity<List<Rating>> getProductRating(@PathVariable Long ProductId,@RequestHeader("Authorization") String jwt) throws UserException,ProductException{
		
		User user = userService.findUserProfileByJwt(jwt);
		
		List<Rating> rating = ratingService.getProductRating(ProductId);
		
		return new ResponseEntity<List<Rating>>(rating,HttpStatus.CREATED);
	}
}
