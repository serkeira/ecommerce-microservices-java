package com.lucascerqueira.ecommerce.checkout.service;

import com.lucascerqueira.ecommerce.checkout.entity.CheckoutEntity;
import com.lucascerqueira.ecommerce.checkout.resource.CheckoutRequest;

import java.util.Optional;

public interface CheckoutService {

    Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest);

    Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status);

}
