package com.example.payment.repository;

import com.example.payment.entity.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentsRepository extends JpaRepository<Payments, String> {

    Payments findByPayMethodAndOrderId(String payMethod, String orderId);
}
