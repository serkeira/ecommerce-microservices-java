package com.lucascerqueira.ecommerce.payment.service;

import com.lucascerqueira.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.lucascerqueira.ecommerce.payment.entity.PaymentEntity;

import java.util.Optional;

public interface PaymentService {

    Optional<PaymentEntity> create(CheckoutCreatedEvent checkoutCreatedEvent);
}
