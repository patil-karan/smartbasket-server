package com.smartbasket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartbasket.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>{

	@Query("SELECT r FROM Review r Where r.product.id = :productId")
	public List<Review> getAllProductReview(@Param("productId")Long productId);
}
