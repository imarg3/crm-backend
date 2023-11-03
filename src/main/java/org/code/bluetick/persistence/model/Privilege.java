package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "privilege")
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="privilegeSeq", sequenceName = "PRIVILEGE_SEQ", allocationSize = 1)
public class Privilege implements Serializable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "privilegeSeq")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Privilege Name must be specified.")
    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Set<Role> roles;
}