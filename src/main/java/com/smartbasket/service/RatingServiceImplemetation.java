package com.smartbasket.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.smartbasket.entity.Product;
import com.smartbasket.entity.Rating;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.repository.RatingRepository;
import com.smartbasket.request.RatingRequest;

@Service
public class RatingServiceImplemetation implements RatingService {

	private RatingRepository ratingRepository;
	private ProductService productService;
	
	
	
	public RatingServiceImplemetation(RatingRepository ratingRepository, ProductService productService) {
		super();
		this.ratingRepository = ratingRepository;
		this.productService = productService;
	}

	@Override
	public Rating createRating(RatingRequest req, User user) throws ProductException {
		Product product = productService.findProductById(req.getProductId());
		
		Rating rating = new Rating();
		rating.setProduct(product);
		rating.setUser(user);
		rating.setRating(req.getRating());
		rating.setCreatedAt(LocalDateTime.now());
		return ratingRepository.save(rating);
	}

	@Override
	public List<Rating> getProductRating(Long productId) {
		
		return ratingRepository.getAllProductRating(productId);
	}

}
