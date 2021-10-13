package pl.local.mpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.Payment;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {
    public List<Payment> findByAppUser(AppUser appUser);
}
