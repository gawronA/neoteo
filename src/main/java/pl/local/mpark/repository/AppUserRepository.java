package pl.local.mpark.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.mpark.entity.AppUser;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
    List<AppUser> findByLastName(String lastName);
    AppUser findById(long id);
    AppUser findByUsername(String username);
}
