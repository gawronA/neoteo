package pl.local.mpark.service;

import org.springframework.stereotype.Service;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.helper.DatabaseResult;

import java.util.List;

@Service("subscriptionService")
public interface SubscriptionService {
    public DatabaseResult addSubscription(Subscription subscription);
    public DatabaseResult deleteSubscription(long id);
    public Subscription getSubscription(long id);
    public List<Subscription> getSubscriptions();
}
