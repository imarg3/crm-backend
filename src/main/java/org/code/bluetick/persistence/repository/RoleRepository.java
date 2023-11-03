package org.code.bluetick.persistence.repository;

import org.code.bluetick.enums.ERole;
import org.code.bluetick.persistence.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findByName(ERole name);
}
