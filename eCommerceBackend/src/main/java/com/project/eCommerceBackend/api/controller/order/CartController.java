package com.project.eCommerceBackend.api.controller.order;

import com.project.eCommerceBackend.api.model.CartResponseBody;
import com.project.eCommerceBackend.model.Cart;
import com.project.eCommerceBackend.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    @GetMapping("/{userId}")
    public ResponseEntity<CartResponseBody> getCart(@PathVariable Long userId) {
        return ResponseEntity.ok(cartService.convertToDTO(cartService.getCartByUserId(userId)));
    }

    @PostMapping("/{userId}/add")
    public ResponseEntity<CartResponseBody> addItem(@PathVariable Long userId,
                                                    @RequestParam Long productId,
                                                    @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.addItemToCart(userId, productId, quantity));
    }

    @PutMapping("/{userId}/update")
    public ResponseEntity<CartResponseBody> updateItem(@PathVariable Long userId,
                                           @RequestParam Long productId,
                                           @RequestParam int quantity) {
        return ResponseEntity.ok(cartService.updateItemQuantity(userId, productId, quantity));
    }

    @DeleteMapping("/{userId}/remove")
    public ResponseEntity<CartResponseBody> removeItem(@PathVariable Long userId,
                                           @RequestParam Long productId) {
        return ResponseEntity.ok(cartService.removeItem(userId, productId));
    }
}
