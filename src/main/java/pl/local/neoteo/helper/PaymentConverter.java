package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.Booking;
import pl.local.neoteo.entity.Payment;
import pl.local.neoteo.service.PaymentService;

import java.util.HashSet;
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
