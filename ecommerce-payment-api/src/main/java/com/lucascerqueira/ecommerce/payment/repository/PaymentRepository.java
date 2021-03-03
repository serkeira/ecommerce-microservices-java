package com.lucascerqueira.ecommerce.payment.repository;

import com.lucascerqueira.ecommerce.payment.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<PaymentEntity, Long> {
}
