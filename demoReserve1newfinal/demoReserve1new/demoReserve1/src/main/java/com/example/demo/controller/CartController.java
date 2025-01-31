package com.example.demo.controller;

import com.example.demo.model.CartItem;
import com.example.demo.service.CartItemService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    private final CartItemService cartItemService;

    public CartController(CartItemService cartItemService) {
        this.cartItemService = cartItemService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addCartItem(@RequestBody CartItem cartItem) {
        cartItemService.saveCartItem(cartItem);
        return ResponseEntity.ok("Item added to cart successfully");
    }

    @PostMapping("/checkout")
    public ResponseEntity<String> checkout() {
        List<CartItem> cartItems = cartItemService.getAllCartItems();

        double subtotal = cartItems.stream().mapToDouble(CartItem::getTotal).sum();

        // Clear the cart after checkout
        cartItemService.clearCart();

        return ResponseEntity.ok("Transaction completed successfully. Subtotal: " + subtotal);
    }

    @GetMapping
    public ResponseEntity<List<CartItem>> getCartItems() {
        return ResponseEntity.ok(cartItemService.getAllCartItems());
    }
}
