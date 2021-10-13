package pl.local.mpark.service;

import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.local.mpark.entity.Subscription;
import pl.local.mpark.helper.DatabaseResult;
import pl.local.mpark.repository.SubscriptionRepository;

import java.util.List;

@Service
class SubscriptionExtServiceImpl {
    private final SubscriptionRepository subscriptionRepository;

    public SubscriptionExtServiceImpl(SubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional(rollbackFor = Exception.class)
    void save(Subscription subscription) {
        this.subscriptionRepository.save(subscription);
    }

    @Transactional(rollbackFor = Exception.class)
    void delete(long id) {
        this.subscriptionRepository.deleteById(id);
    }
}

@Service("subscriptionService")
public class SubscriptionServiceImpl implements SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final SubscriptionExtServiceImpl subscriptionExtService;

    public SubscriptionServiceImpl(SubscriptionRepository subscriptionRepository, SubscriptionExtServiceImpl subscriptionExtService) {
        this.subscriptionRepository = subscriptionRepository;
        this.subscriptionExtService = subscriptionExtService;
    }

    public DatabaseResult addSubscription(Subscription subscription) {
        try {
            this.subscriptionExtService.save(subscription);
        }
        catch (Exception ex) {
            return DatabaseResult.AlreadyExist;
        }
        return DatabaseResult.Success;
    }

    public DatabaseResult deleteSubscription(long id) {
        try {
            this.subscriptionExtService.delete(id);
        }
        catch (Exception ex) {
            return DatabaseResult.HasDependencies;
        }
        return DatabaseResult.Success;
    }

    @Transactional
    public Subscription getSubscription(long id) {
        var sub = this.subscriptionRepository.findById(id);
        return sub.orElse(null);
    }
    @Transactional
    public List<Subscription> getSubscriptions() {
        return this.subscriptionRepository.findAll();
    }
}
