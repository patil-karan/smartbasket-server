package com.smartbasket.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.smartbasket.entity.Product;
import com.smartbasket.exception.ProductException;
import com.smartbasket.request.ProductRequest;


public interface ProductService {
	
	public Product createProduct(ProductRequest req);
	public String deleteProduct(Long productId) throws ProductException;
	public Product updateProduct(Long productId,Product product) throws ProductException;
	public Product findProductById(Long productId) throws ProductException;
	public List<Product> findProductByCategory(String category);
	public Page<Product> getAllProduct(String category,List<String> colors, List<String> size, Integer minPrice, Integer maxPrice,
			Integer minDiscount, String sort,String stock,Integer pageNumber,Integer pageSize);
	
	public List<Product> findAllProducts();

}
