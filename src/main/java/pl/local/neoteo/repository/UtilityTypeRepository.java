package pl.local.neoteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.neoteo.entity.UtilityType;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UtilityTypeRepository extends JpaRepository<UtilityType, Long> {
}
