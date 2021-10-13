package pl.local.mpark.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.repository.PaymentRepository;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Service
class PaymentExtServiceImpl {

    private final PaymentRepository paymentRepository;

    public PaymentExtServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    @Transactional
    void save(Payment payment) {
        this.paymentRepository.save(payment);
    }

    @Transactional
    void delete(long id) {
        this.paymentRepository.deleteById(id);
    }
}

@Service("paymentService")
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PaymentExtServiceImpl paymentExtService;
    private final AppUserService appUserService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentExtServiceImpl paymentExtService, AppUserService appUserService) {
        this.paymentRepository = paymentRepository;
        this.paymentExtService = paymentExtService;
        this.appUserService = appUserService;
    }


    @Override
    public DatabaseResult addPayment(Payment payment) {
        try {
            this.paymentExtService.save(payment);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    @Override
    @Transactional
    public DatabaseResult updatePayment(Payment payment) {
        var dbPaymentFind = this.paymentRepository.findById(payment.getId());
        Payment dbPayment = dbPaymentFind.orElse(null);
        if(dbPayment == null) return DatabaseResult.Error;

        if(payment.getName() != null && !payment.getName().isEmpty()) dbPayment.setName(payment.getName());
        dbPayment.setAmount(payment.getAmount());
        dbPayment.setPaid(payment.isPaid());

        return addPayment(dbPayment);
    }

    @Override
    public DatabaseResult deletePayment(long id) {
        Payment payment = getPayment(id);
        if(payment == null) return DatabaseResult.Error;

        AppUser paymentUser = payment.getAppUser();
        payment.setAppUser(null);
        this.appUserService.editUser(paymentUser);

        try {
            this.paymentExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Override
    public Payment getPayment(long id) {
        var pay = this.paymentRepository.findById(id);
        return pay.orElse(null);
    }

    @Override
    public List<Payment> getPayments() {
        return this.paymentRepository.findAll();
    }

    @Override
    public List<Payment> getFreePayments() {
        var payments = this.paymentRepository.findByAppUser(null);
        return payments;
    }

    @Override
    public double getTotalBalance() {
        var payments = getPayments();
        double balance = 0;
        for(Payment payment : payments) {
            balance += payment.getAmount();
        }
        return balance;
    }

    @Override
    public double getTotalPaidBalance() {
        var payments = getPayments().stream().filter(Payment::isPaid).collect(Collectors.toList());
        double balance = 0;
        for(Payment payment : payments) {
            balance += payment.getAmount();
        }
        return balance;
    }
}
