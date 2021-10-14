package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Payment;
import pl.local.neoteo.entity.Subscription;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service("paymentService")
public interface PaymentService {
    public DatabaseResult addPayment(Payment payment);
    public DatabaseResult updatePayment(Payment payment);
    public DatabaseResult deletePayment(long id);
    public Payment getPayment(long id);
    public List<Payment> getPayments();
    public List<Payment> getFreePayments();
    public double getTotalBalance();
    public double getTotalPaidBalance();
}
