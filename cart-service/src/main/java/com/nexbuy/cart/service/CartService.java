package com.nexbuy.cart.service;

import com.nexbuy.cart.dto.AddCartItemRequest;
import com.nexbuy.cart.dto.CartResponse;
import com.nexbuy.cart.dto.UpdateCartItemRequest;

public interface CartService {

    CartResponse addItem(AddCartItemRequest request);

    CartResponse getCart();

    CartResponse updateItem(
            Long productId,
            UpdateCartItemRequest request);

    void removeItem(Long productId);

    void clearCart();
}