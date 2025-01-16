package com.smartbasket.service;

import com.smartbasket.entity.Cart;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.request.AddItemRequest;

public interface CartService {

public Cart CreateCart(User user);
	
	public String addCartItem(Long userId,AddItemRequest req)throws ProductException;
	
	public Cart findUserCart(Long userId);
}
