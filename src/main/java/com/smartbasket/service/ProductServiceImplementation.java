package com.smartbasket.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale.Category;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.smartbasket.entity.Product;
import com.smartbasket.exception.ProductException;
import com.smartbasket.repository.ProductRepository;
import com.smartbasket.request.ProductRequest;


@Service
public class ProductServiceImplementation implements ProductService{
	
	private CategoryRepository categoryRepository;
	private ProductRepository productRepository;
	
	
	public ProductServiceImplementation(CategoryRepository categoryRepository, ProductRepository productRepository) {
		this.categoryRepository = categoryRepository;
		this.productRepository = productRepository;
	}

	@Override
	public Product createProduct(ProductRequest req) {
		
		Category topLevelCategory = categoryRepository.findByCategoryName(req.getTopLevelCategory());
		
		if (topLevelCategory == null) {
			
			Category topLevel = new Category();
			topLevel.setCategoryName(req.getTopLevelCategory());
			topLevel.setLevel(1);
			
			topLevelCategory = categoryRepository.save(topLevel);
			
		}
		
		Category secondLevelCategory = categoryRepository.findByNameAndParent(req.getSecondLevelCategory(), topLevelCategory.getCategoryName());
		
		if (secondLevelCategory == null) {
			Category secondLevel = new Category();
			secondLevel.setCategoryName(req.getSecondLevelCategory());
			secondLevel.setParentCategory(topLevelCategory);
			secondLevel.setLevel(2);
			
			secondLevelCategory = categoryRepository.save(secondLevel);
			
		}
		
		Category thirdLevelCategory = categoryRepository.findByNameAndParent(req.getThirdLevelCategory(), secondLevelCategory.getCategoryName());
		if (thirdLevelCategory == null) {
			Category thirdLevel = new Category();
			thirdLevel.setCategoryName(req.getThirdLevelCategory());
			thirdLevel.setParentCategory(secondLevelCategory);
			thirdLevel.setLevel(3);
			
			thirdLevelCategory = categoryRepository.save(thirdLevel);
		}
		
		Product product = new Product();
		product.setTitle(req.getTitle());
		product.setColor(req.getColor());
		product.setDiscription(req.getDiscription());
		product.setDiscountedPrice(req.getDiscountedPrice());
		product.setDiscountedPercent(req.getDiscountedPersent());
		product.setImageUrl(req.getImageUrl());
		product.setBrand(req.getBrand());
		product.setPrice(req.getPrice());
		product.setSizes(req.getSize());
		product.setQuantity(req.getQuantity());
		product.setCategory(thirdLevelCategory);
		product.setCreatedAt(LocalDateTime.now());
		
		Product savedProduct = productRepository.save(product);
		return savedProduct;
	}

	@Override
	public String deleteProduct(Long productId) throws ProductException {
		Product product = findProductById(productId);
		product.getSizes().clear();
		productRepository.delete(product);
		return "Product Deleted Successfully";
	}

	@Override
	public Product updateProduct(Long productId, Product product) throws ProductException {
		Product updateProduct = new Product();
		
		if (product.getQuantity() != 0 ) {
			updateProduct.setQuantity(product.getQuantity());
		}
		return productRepository.save(updateProduct);
	}

	@Override
	public Product findProductById(Long productId) throws ProductException {
		Optional<Product> optional = productRepository.findById(productId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		throw new ProductException("Product not found With id - "+productId );
	}

	@Override
	public List<Product> findProductByCategory(String category) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Page<Product> getAllProduct(String category, List<String> colors, List<String> size, Integer minPrice,
			Integer maxPrice, Integer minDiscount, String sort, String stock, Integer pageNumber, Integer pageSize) {


		Pageable pageable = PageRequest.of(pageNumber, pageSize);
		
		List<Product> products = productRepository.filterProducts(category, minPrice, maxPrice, minDiscount, sort);
		
		if (!colors.isEmpty()) {
			products = products.stream().filter(product->colors.stream().anyMatch(c->c.equalsIgnoreCase(product.getColor()))).collect(Collectors.toList());
		}
		
		if (stock != null) {
			if(stock.equals("in_stock")){
				products = products.stream().filter(p->p.getQuantity()>0).collect(Collectors.toList()); 
			}
			else if(stock.equals("out_of_stock")){
				products = products.stream().filter(p->p.getQuantity()<1).collect(Collectors.toList()); 
			}
		}
		
		int startIndex = (int)pageable.getOffset();
		int endIndex = Math.min(startIndex+pageable.getPageSize(),products.size());
		
		List<Product> pageContent =products.subList(startIndex, endIndex);
		
		Page<Product> filteredProducts = new PageImpl<>(pageContent, pageable, products.size());
		
		return filteredProducts;
	}

	@Override
	public List<Product> findAllProducts() {
		return productRepository.findAll();
	}

}
