package com.smartbasket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.smartbasket.entity.Rating;

public interface RatingRepository extends JpaRepository<Rating, Long>{

	@Query("SELECT r FROM Rating r Where r.product.id = :productId")
	public List<Rating> getAllProductRating(@Param("productId")Long productId);
}
