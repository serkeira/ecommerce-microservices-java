package com.lucascerqueira.ecommerce.checkout.service;

import com.lucascerqueira.ecommerce.checkout.entity.CheckoutEntity;
import com.lucascerqueira.ecommerce.checkout.entity.ShippingEntity;
import com.lucascerqueira.ecommerce.checkout.event.CheckoutCreatedEvent;
import com.lucascerqueira.ecommerce.checkout.repository.CheckoutRepository;
import com.lucascerqueira.ecommerce.checkout.resource.CheckoutRequest;
import com.lucascerqueira.ecommerce.checkout.streaming.CheckoutCreatedSource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class CheckoutServiceImpl implements CheckoutService {

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCreatedSource checkoutCreatedSource;

    @Override
    public Optional<CheckoutEntity> create(CheckoutRequest checkoutRequest) {
        log.info("M=create, checkoutRequest={}", checkoutRequest);
        final CheckoutEntity checkoutEntity = CheckoutEntity.builder()
                .code(UUID.randomUUID().toString())
                .status(CheckoutEntity.Status.CREATED)
                .saveAddress(checkoutRequest.getSaveAddress())
                .saveInformation(checkoutRequest.getSaveInfo())
                .shipping(ShippingEntity.builder()
                        .address(checkoutRequest.getAddress())
                        .complement(checkoutRequest.getComplement())
                        .country(checkoutRequest.getCountry())
                        .state(checkoutRequest.getState())
                        .cep(checkoutRequest.getCep())
                        .build())
                .build();
//        checkoutEntity.setItems(checkoutRequest.getProducts()
//                .stream()
//                .map(product -> CheckoutItemEntity.builder()
//                        .checkout(checkoutEntity)
//                        .product(product)
//                        .build())
//                .collect(Collectors.toList()));
        final CheckoutEntity entity = checkoutRepository.save(checkoutEntity);
        final CheckoutCreatedEvent checkoutCreatedEvent = CheckoutCreatedEvent.newBuilder()
                .setCheckoutCode(entity.getCode())
                .setStatus(entity.getStatus().name())
                .build();
        checkoutCreatedSource.output().send(MessageBuilder.withPayload(checkoutCreatedEvent).build());
        return Optional.of(entity);
    }

    @Override
    public Optional<CheckoutEntity> updateStatus(String checkoutCode, CheckoutEntity.Status status) {
        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(checkoutCode).orElse(CheckoutEntity.builder().build());
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        return Optional.of(checkoutRepository.save(checkoutEntity));
    }
}

