package com.microservices.project.paymentservice.service;

import com.microservices.project.paymentservice.entity.TransactionDetails;
import com.microservices.project.paymentservice.model.PaymentMode;
import com.microservices.project.paymentservice.model.PaymentRequest;
import com.microservices.project.paymentservice.model.PaymentResponse;
import com.microservices.project.paymentservice.repository.TransactionDetailsRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@Log4j2
public class PaymentServiceImpl implements PaymentService {

    @Autowired
    private TransactionDetailsRepository transactionDetailsRepository;

    @Override
    public long doPayment(PaymentRequest paymentRequest) {
        log.info("Recording payment details: {}", paymentRequest);

        TransactionDetails transactionDetails =
                TransactionDetails.builder()
                        .paymentDate(Instant.now())
                        .paymentMode(paymentRequest.getPaymentMode().name())
                        .paymentStatus("SUCCESS")
                        .orderId(paymentRequest.getOrderId())
                        .referenceNumber(paymentRequest.getReferenceNumber())
                        .amount(paymentRequest.getAmount())
                        .build();

        transactionDetailsRepository.save(transactionDetails);

        log.info("Transaction Completed with ID: {}", transactionDetails.getId());

        return transactionDetails.getId();
    }

    @Override
    public PaymentResponse getPaymentDetailsByOrderId(long orderId) {
        log.info("Getting payment details of Order Id: {}", orderId);

        TransactionDetails transactionDetails = transactionDetailsRepository.findByOrderId(orderId);

        return PaymentResponse.builder()
                .paymentDate(transactionDetails.getPaymentDate())
                .paymentId(transactionDetails.getId())
                .orderId(transactionDetails.getOrderId())
                .paymentMode(PaymentMode.valueOf(transactionDetails.getPaymentMode()))
                .amount(transactionDetails.getAmount())
                .status(transactionDetails.getPaymentStatus())
                .build();

    }
}
