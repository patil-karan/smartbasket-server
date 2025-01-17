package com.smartbasket.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.smartbasket.entity.Cart;
import com.smartbasket.entity.CartItems;
import com.smartbasket.entity.Product;
import com.smartbasket.entity.User;
import com.smartbasket.exception.CartItemException;
import com.smartbasket.exception.UserException;
import com.smartbasket.repository.CartItemRepository;

@Service
public class CartItemServiceImplementation implements CartItemService{

	private CartItemRepository cartItemRepository;
	private UserService userService;
	
	
	
	public CartItemServiceImplementation(CartItemRepository cartItemRepository, UserService userService) {
		super();
		this.cartItemRepository = cartItemRepository;
		this.userService = userService;
	}

	@Override
	public CartItems creaCartItems(CartItems cartItems) {
		cartItems.setQuantity(1);
		cartItems.setPrice(cartItems.getProduct().getPrice()*cartItems.getQuantity());
		cartItems.setDiscountedPrice(cartItems.getProduct().getDiscountedPrice()*cartItems.getQuantity());
		
		CartItems createdCartItem = cartItemRepository.save(cartItems);
		
		return createdCartItem;
	}

	@Override
	public CartItems updateCartItems(Long userId, Long id, CartItems cartItems)throws CartItemException, UserException {
		CartItems item = findCartItemById(id);
		User user = userService.findById(item.getId());
		
		if (user.getId().equals(userId)) {
			item.setQuantity(cartItems.getQuantity());
			item.setPrice(item.getQuantity()*item.getProduct().getPrice());
			item.setDiscountedPrice(item.getProduct().getDiscountedPrice()*item.getQuantity());
		}
		return cartItemRepository.save(item);
	}

	@Override
	public CartItems isCartItemExist(Cart cart, Product product, String size, Long userId) {
		CartItems cartItem = cartItemRepository.isCartItemExist(cart, product, size, userId);
		return cartItem;
	}

	@Override
	public void removeCartItem(Long userId, Long cartItemId) throws CartItemException, UserException {
		CartItems cartItem = findCartItemById(cartItemId);
		
		User user = userService.findById(cartItem.getUserId());
		
		User reqUser = userService.findById(userId);
		
		if (user.getId().equals(reqUser.getId())) {
			cartItemRepository.deleteById(cartItemId);
		}else {
			throw new UserException("You Cant Remove another users item");
		}
	}

	@Override
	public CartItems findCartItemById(Long cartItemId) throws CartItemException {
		Optional<CartItems> optional = cartItemRepository.findById(cartItemId);
		
		if (optional.isPresent()) {
			return optional.get();
		}
		
		throw new CartItemException("Cart Item Not Found with id : "+cartItemId);
	}

}
