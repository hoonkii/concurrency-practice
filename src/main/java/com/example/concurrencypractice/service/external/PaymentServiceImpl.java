package com.example.concurrencypractice.service.external;

import org.springframework.stereotype.Service;

@Service
public class PaymentServiceImpl implements PaymentService{
    @Override
    public void doPay() throws InterruptedException {
        Thread.sleep(1000);
    }
}
