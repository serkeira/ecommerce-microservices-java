package com.lucascerqueira.ecommerce.checkout.listener;

import com.lucascerqueira.ecommerce.checkout.entity.CheckoutEntity;
import com.lucascerqueira.ecommerce.checkout.repository.CheckoutRepository;
import com.lucascerqueira.ecommerce.checkout.streaming.PaymentPaidSink;
import com.lucascerqueira.ecommerce.payment.event.PaymentCreatedEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PaymentPaidListener {

    private final CheckoutRepository checkoutRepository;

    @StreamListener(PaymentPaidSink.INPUT)
    public void handler(PaymentCreatedEvent event) {

        final CheckoutEntity checkoutEntity = checkoutRepository.findByCode(event.getCheckoutCode().toString()).orElseThrow();
        checkoutEntity.setStatus(CheckoutEntity.Status.APPROVED);
        checkoutRepository.save(checkoutEntity);


    }
}
