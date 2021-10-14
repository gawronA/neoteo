package pl.local.neoteo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.local.neoteo.entity.UserRole;

import javax.transaction.Transactional;

@Transactional
@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long> {
    UserRole findByRole(String role);
}
