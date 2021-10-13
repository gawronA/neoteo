package pl.local.mpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.mpark.entity.AppUser;
import pl.local.mpark.entity.Booking;
import pl.local.mpark.entity.Payment;
import pl.local.mpark.entity.Subscription;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    public List<Booking> findByAppUser(Booking booking);
}
