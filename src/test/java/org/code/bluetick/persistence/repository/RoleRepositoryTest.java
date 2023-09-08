package org.code.bluetick.persistence.repository;

import org.code.bluetick.DatabaseTest;
import org.code.bluetick.enums.ERole;
import org.code.bluetick.persistence.model.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class RoleRepositoryTest extends DatabaseTest {
    @Autowired
    private RoleRepository roleRepository;

    @Test
    @DisplayName("create role")
    void testCreateRoles() {
        var manager = Role.builder()
                .name(ERole.ROLE_ADMIN)
                .build();
        var user = Role.builder()
                .name(ERole.ROLE_AGENT)
                .build();

        roleRepository.saveAll(List.of(manager, user));
        List<Role> roles = roleRepository.findAll();
        assertThat(roles).hasSize(2);
        assertThat(roles.get(0).getName()).isEqualTo(ERole.ROLE_ADMIN.name());
    }
}