package pl.local.neoteo.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.Payment;
import pl.local.neoteo.service.PaymentService;

import java.util.HashSet;
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
