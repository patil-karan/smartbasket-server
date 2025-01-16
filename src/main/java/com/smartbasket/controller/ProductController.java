package com.smartbasket.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.smartbasket.entity.Product;
import com.smartbasket.exception.ProductException;
import com.smartbasket.service.ProductService;


@RestController
@RequestMapping("/api")
public class ProductController {

	@Autowired
	private ProductService productService;
	
	@GetMapping("/products")
	public ResponseEntity<Page<Product>> findProductByCategory(@RequestParam String category, @RequestParam List<String> color,
			@RequestParam List<String> size,@RequestParam Integer minPrice,@RequestParam Integer maxPrice,@RequestParam Integer minDiscount,
			@RequestParam String sort,@RequestParam String stock,@RequestParam Integer pagenumber,@RequestParam Integer pageSize){
		
		Page<Product> productPage = productService.getAllProduct(category, color, size, minPrice, maxPrice, minDiscount, sort, stock, pagenumber, pageSize);
		
		return new ResponseEntity<Page<Product>>(productPage,HttpStatus.ACCEPTED);
	}
	
	@GetMapping("/products/id/{productId}")
	public ResponseEntity<Product> findProductById(@PathVariable Long productId)throws ProductException{
		
		Product product = productService.findProductById(productId);
		
		return new ResponseEntity<Product>(product,HttpStatus.ACCEPTED);
	}
	
}
