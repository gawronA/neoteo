package pl.local.mpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import pl.local.mpark.entity.UserCard;

@Transactional
@Repository
public interface CardRepository extends JpaRepository<UserCard, Long> {
    public UserCard findByNumber(String number);
}
