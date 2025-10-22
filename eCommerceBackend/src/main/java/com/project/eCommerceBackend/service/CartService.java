package com.project.eCommerceBackend.service;

import com.project.eCommerceBackend.api.model.CartItemBody;
import com.project.eCommerceBackend.api.model.CartResponseBody;
import com.project.eCommerceBackend.model.Cart;
import com.project.eCommerceBackend.model.CartItem;
import com.project.eCommerceBackend.model.LocalUser;
import com.project.eCommerceBackend.model.Product;
import com.project.eCommerceBackend.model.dao.CartDAO;
import com.project.eCommerceBackend.model.dao.LocalUserDAO;
import com.project.eCommerceBackend.model.dao.ProductDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CartService {

    @Autowired
    private CartDAO cartDAO;

    @Autowired
    private ProductDAO productDAO;

    @Autowired
    private LocalUserDAO localUserDAO;

    public Cart getCartByUserId(Long userId) {
        LocalUser user = localUserDAO.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found!!!"));

        return cartDAO.findByUser(user).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(user);
            return cartDAO.save(newCart);
        });
    }

    public CartResponseBody addItemToCart(Long userId, Long productId, int quantity) {

        Cart cart = getCartByUserId(userId);
        Product product = productDAO.findById(productId)
                .orElseThrow(() -> new RuntimeException("PRODUCT NOT FOUND!!!"));

        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {
            existingItem.get().setQuantity(existingItem.get().getQuantity() + quantity);
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(quantity);
            cart.getItems().add(newItem);
        }
        cartDAO.save(cart);
        return convertToDTO(cart);
    }

    public CartResponseBody updateItemQuantity(Long userId, Long productId, int quantity) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .ifPresent(item -> item.setQuantity(quantity));
        cartDAO.save(cart);
        return convertToDTO(cart);
    }

    public CartResponseBody removeItem(Long userId, Long productId) {
        Cart cart = getCartByUserId(userId);
        cart.getItems().removeIf(item -> item.getProduct().getId().equals(productId));
        cartDAO.save(cart);
        return convertToDTO(cart);
    }

    public CartResponseBody convertToDTO(Cart cart) {
        List<CartItemBody> itemDTOs = cart.getItems().stream()
                .map(item -> new CartItemBody(
                        item.getProduct().getId(),
                        item.getProduct().getName(),
                        item.getQuantity(),
                        item.getProduct().getPrice()
                ))
                .toList();

        double total = itemDTOs.stream()
                .mapToDouble(i -> i.getPrice() * i.getQuantity())
                .sum();

        return new CartResponseBody(cart.getUser().getId(), itemDTOs, total);
    }

}
