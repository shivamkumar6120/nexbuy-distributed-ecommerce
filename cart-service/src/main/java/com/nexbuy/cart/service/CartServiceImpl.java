package com.nexbuy.cart.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.nexbuy.cart.client.ProductClient;
import com.nexbuy.cart.dto.AddCartItemRequest;
import com.nexbuy.cart.dto.CartItemResponse;
import com.nexbuy.cart.dto.CartResponse;
import com.nexbuy.cart.dto.ProductResponse;
import com.nexbuy.cart.dto.UpdateCartItemRequest;
import com.nexbuy.cart.entity.Cart;
import com.nexbuy.cart.entity.CartItem;
import com.nexbuy.cart.exception.CartItemNotFoundException;
import com.nexbuy.cart.exception.CartNotFoundException;
import com.nexbuy.cart.exception.InsufficientStockException;
import com.nexbuy.cart.exception.ProductNotFoundException;
import com.nexbuy.cart.repository.CartItemRepository;
import com.nexbuy.cart.repository.CartRepository;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;
    private final ProductClient productClient;

    @Override
    @Transactional
    public CartResponse addItem(AddCartItemRequest request) {

        String userEmail = getCurrentUserEmail();

        Cart cart = cartRepository
                .findByUserEmail(userEmail)
                .orElseGet(() -> {

                    Cart newCart = new Cart();
                    newCart.setUserEmail(userEmail);

                    return cartRepository.save(newCart);
                });

        ProductResponse product;

        try {
            product = productClient.getProductById(
                    request.getProductId());
        } catch (Exception ex) {
            throw new ProductNotFoundException(
                    "Product not found with id: "
                            + request.getProductId());
        }

        if (product.getStockQuantity()
                < request.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock for product id: "
                            + product.getId());
        }

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                request.getProductId())
                        .orElse(null);

        if (cartItem == null) {

            cartItem = new CartItem();
            cartItem.setCart(cart);
            cartItem.setProductId(
                    request.getProductId());
            cartItem.setQuantity(
                    request.getQuantity());

        } else {

            int newQuantity =
                    cartItem.getQuantity()
                            + request.getQuantity();

            if (product.getStockQuantity()
                    < newQuantity) {

                throw new InsufficientStockException(
                        "Insufficient stock for product id: "
                                + product.getId());
            }

            cartItem.setQuantity(newQuantity);
        }

        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    public CartResponse getCart() {

        String userEmail = getCurrentUserEmail();

        Cart cart = cartRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found"));

        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public CartResponse updateItem(
            Long productId,
            UpdateCartItemRequest request) {

        String userEmail = getCurrentUserEmail();

        Cart cart = cartRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found"));

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new CartItemNotFoundException(
                                        "Product not found in cart"));

        ProductResponse product =
                productClient.getProductById(productId);

        if (product.getStockQuantity()
                < request.getQuantity()) {

            throw new InsufficientStockException(
                    "Insufficient stock for product id: "
                            + productId);
        }

        cartItem.setQuantity(request.getQuantity());

        cartItemRepository.save(cartItem);

        return buildCartResponse(cart);
    }

    @Override
    @Transactional
    public void removeItem(Long productId) {

        String userEmail = getCurrentUserEmail();

        Cart cart = cartRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found"));

        CartItem cartItem =
                cartItemRepository
                        .findByCartIdAndProductId(
                                cart.getId(),
                                productId)
                        .orElseThrow(() ->
                                new CartItemNotFoundException(
                                        "Product not found in cart"));

        cartItemRepository.delete(cartItem);
    }

    @Override
    @Transactional
    public void clearCart() {

        String userEmail = getCurrentUserEmail();

        Cart cart = cartRepository
                .findByUserEmail(userEmail)
                .orElseThrow(() ->
                        new CartNotFoundException(
                                "Cart not found"));

        cart.getItems().clear();

        cartRepository.save(cart);
    }

    private String getCurrentUserEmail() {

        return SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
    }

    private CartResponse buildCartResponse(Cart cart) {

        List<CartItemResponse> itemResponses =
                new ArrayList<>();

        BigDecimal totalAmount =
                BigDecimal.ZERO;

        for (CartItem item : cart.getItems()) {

            ProductResponse product =
                    productClient.getProductById(
                            item.getProductId());

            BigDecimal subtotal =
                    product.getPrice()
                            .multiply(
                                    BigDecimal.valueOf(
                                            item.getQuantity()));

            totalAmount =
                    totalAmount.add(subtotal);

            itemResponses.add(
                    new CartItemResponse(
                            product.getId(),
                            product.getName(),
                            product.getPrice(),
                            item.getQuantity(),
                            subtotal));
        }

        return new CartResponse(
                cart.getId(),
                cart.getUserEmail(),
                itemResponses,
                totalAmount);
    }
}