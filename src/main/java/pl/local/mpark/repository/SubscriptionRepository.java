package pl.local.mpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.mpark.entity.Subscription;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {
    Subscription findByName(String name);
}
