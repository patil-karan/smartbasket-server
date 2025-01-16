package com.smartbasket.service;

import com.smartbasket.entity.Cart;
import com.smartbasket.entity.CartItems;
import com.smartbasket.entity.Product;
import com.smartbasket.exception.CartItemException;
import com.smartbasket.exception.UserException;

public interface CartItemService {

	public CartItems creaCartItems(CartItems cartItems);
	
	public CartItems updateCartItems(Long userId, Long id,CartItems cartItems)throws CartItemException,UserException;
	
	public CartItems isCartItemExist(Cart cart, Product product,String size, Long userId);
	
	public void removeCartItem(Long userId, Long cartItemId)throws CartItemException,UserException;
	
	public CartItems findCartItemById(Long cartItemId) throws CartItemException;
}
