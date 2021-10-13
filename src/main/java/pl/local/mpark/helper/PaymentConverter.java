package pl.local.mpark.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.mpark.entity.Booking;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.service.BookingService;
import pl.local.mpark.service.PaymentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentConverter implements Converter<String, Set<Payment>> {

    private final PaymentService paymentService;

    @Autowired
    public PaymentConverter(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Set<Payment> convert(String s) {
        Payment payment = this.paymentService.getPayment(Long.parseLong(s));
        if(payment == null) return null;
        Set<Payment> payments = new HashSet<>();
        payments.add(payment);
        return payments;
    }
}
