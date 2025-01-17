package com.smartbasket.service;

import org.springframework.stereotype.Service;

import com.smartbasket.entity.Cart;
import com.smartbasket.entity.CartItems;
import com.smartbasket.entity.Product;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.repository.CartRepository;
import com.smartbasket.request.AddItemRequest;

@Service
public class CartServiceImplementation implements CartService{

	private CartRepository cartRepository;
	private CartItemService cartItemService;
	private ProductService productService;
	
	
	
	public CartServiceImplementation(CartRepository cartRepository, CartItemService cartItemService,ProductService productService) {
		this.cartRepository = cartRepository;
		this.cartItemService = cartItemService;
		this.productService = productService;
	}

	@Override
	public Cart CreateCart(User user) {
		Cart cart = new Cart();
		cart.setUser(user);
		return cartRepository.save(cart);
	}

	@Override
	public String addCartItem(Long userId, AddItemRequest req) throws ProductException {
		Cart cart = cartRepository.findByUserId(userId);
		Product product = productService.findProductById(req.getProductId());
		
		CartItems isPresent = cartItemService.isCartItemExist(cart, product, req.getSize(), userId);
		
		if (isPresent==null) {
			CartItems cartItem = new CartItems();
			cartItem.setProduct(product);
			cartItem.setCart(cart);
			cartItem.setQuantity(req.getQuantity());
			cartItem.setUserId(userId);
			
			Integer price = req.getQuantity()*product.getDiscountedPrice();
			cartItem.setPrice(price);
			cartItem.setSize(req.getSize());
			
			CartItems createdCartItems = cartItemService.creaCartItems(cartItem);
			cart.getCartItems().add(createdCartItems);
		}
		return "Item Add To Cart";
	}

	@Override
	public Cart findUserCart(Long userId) {
		Cart cart = cartRepository.findByUserId(userId);
		
		Integer totalPrice=0;
		Integer totalDiscountedPrice=0;
		Integer totalItems=0;
		
		for(CartItems cartItem: cart.getCartItems()) {
			totalPrice += cartItem.getPrice();
			totalDiscountedPrice += cartItem.getDiscountedPrice();
			totalItems += cartItem.getQuantity();
		}
		
		cart.setTotalDiscountedPrice(totalDiscountedPrice);
		cart.setTotalItem(totalItems);
		cart.setTotalPrice(totalPrice);
		cart.setDiscount(totalPrice-totalDiscountedPrice);
		return cartRepository.save(cart);
	}

	
}
