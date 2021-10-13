package pl.local.mpark.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.service.PaymentService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class PaymentListConverter implements Converter<String[], Set<Payment>> {

    private final PaymentService paymentService;

    @Autowired
    public PaymentListConverter(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @Override
    public Set<Payment> convert(String[] s) {
        Set<Payment> payments = new HashSet<Payment>();
        for(String id : s) {
            Payment payment = this.paymentService.getPayment(Long.parseLong(id));
            if(payment == null) return null;
            payments.add(payment);
        }
        return payments;
    }
}
