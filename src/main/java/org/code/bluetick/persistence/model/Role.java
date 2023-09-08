package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.code.bluetick.enums.ERole;

import java.util.Set;

@Entity
@Table(name = "role", schema = "crm")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="roleSeq", sequenceName = "ROLE_SEQ", allocationSize = 1)
public class Role {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "roleSeq")
    private Long id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Role Name must be specified.")
    private ERole name;

    @ManyToMany(mappedBy = "roles")
    private Set<User> users;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges;
}
