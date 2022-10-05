package com.microservices.project.paymentservice.service;

import com.microservices.project.paymentservice.model.PaymentRequest;
import com.microservices.project.paymentservice.model.PaymentResponse;

public interface PaymentService {

    long doPayment(PaymentRequest paymentRequest);

    PaymentResponse getPaymentDetailsByOrderId(long orderId);
}
