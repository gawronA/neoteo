package pl.local.neoteo.helper;

import org.springframework.core.convert.converter.Converter;
import pl.local.neoteo.entity.Payment;
import pl.local.neoteo.entity.Subscription;
import pl.local.neoteo.service.SubscriptionService;

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
