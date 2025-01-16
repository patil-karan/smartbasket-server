package com.smartbasket.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.response.ApiResponse;
import com.smartbasket.entity.Cart;
import com.smartbasket.entity.User;
import com.smartbasket.exception.ProductException;
import com.smartbasket.exception.UserException;
import com.smartbasket.request.AddItemRequest;
import com.smartbasket.service.CartService;
import com.smartbasket.service.UserService;

@RequestMapping("/api/cart")
@RestController
public class CartController {
	
	@Autowired
	private CartService cartService;
	@Autowired
	private UserService userService;

	
	@GetMapping("/")
	public ResponseEntity<Cart> findUserCart(@RequestHeader("Authorization") String jwt) throws UserException{
		User user = userService.findUserProfileByJwt(jwt);
		Cart cart = cartService.findUserCart(user.getId());
		return new ResponseEntity<Cart>(cart,HttpStatus.OK);
	}
	
	@PutMapping("/add")
	public ResponseEntity<ApiResponse> addItemToCart(@RequestBody AddItemRequest request,@RequestHeader("Authorization") String jwt)throws UserException , ProductException{
		User user = userService.findUserProfileByJwt(jwt);
		
		cartService.addCartItem(user.getId(), request);
		
		ApiResponse response = new ApiResponse();
		response.setMessage("Items added Successfully");
		response.setStatus(true);
		
		return new ResponseEntity<ApiResponse>(response,HttpStatus.OK);
	}
	
}
