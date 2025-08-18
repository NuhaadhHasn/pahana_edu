package com.example.pahanaedu.service;

import com.example.pahanaedu.model.Payment;

public interface IPaymentService {
    boolean recordPayment(Payment payment);
}