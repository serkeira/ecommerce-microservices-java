package com.lucascerqueira.ecommerce.checkout.service;

import com.lucascerqueira.ecommerce.checkout.entity.CheckoutEntity;
import com.lucascerqueira.ecommerce.checkout.repository.CheckoutRepository;
import com.lucascerqueira.ecommerce.checkout.resource.CheckoutRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(UUID.randomUUID().toString())
                .status(CheckoutEntity.Status.CREATED)
                .build();
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}
