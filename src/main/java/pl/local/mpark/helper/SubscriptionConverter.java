package pl.local.mpark.helper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.service.AppUserRoleService;
import pl.local.mpark.service.SubscriptionService;

import java.util.List;

public class SubscriptionConverter implements Converter<String, Subscription> {

    private final SubscriptionService subscriptionService;

    public SubscriptionConverter(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    public Subscription convert(String s) {
        Subscription subscription = subscriptionService.getSubscription(Long.parseLong(s));
        return subscription;
    }
}
