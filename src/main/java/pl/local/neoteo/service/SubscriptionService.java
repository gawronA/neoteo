package pl.local.neoteo.service;

import org.springframework.stereotype.Service;
import pl.local.neoteo.entity.Subscription;
import pl.local.neoteo.helper.DatabaseResult;

import java.util.List;

@Service("subscriptionService")
public interface SubscriptionService {
    public DatabaseResult addSubscription(Subscription subscription);
    public DatabaseResult deleteSubscription(long id);
    public Subscription getSubscription(long id);
    public List<Subscription> getSubscriptions();
}
