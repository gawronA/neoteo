package pl.local.neoteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.neoteo.entity.Property;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface PropertyRepository extends JpaRepository<Property, Long> {

}
