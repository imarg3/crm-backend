package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Table(name = "customer")
@Getter@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@SequenceGenerator(name="customerSeq", sequenceName = "CUSTOMER_SEQ", allocationSize = 1)
public class Customer {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSeq")
    private Long id;

    @Column(name = "name", nullable = false)
    @NotNull(message = "Name must be specified.")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @NotNull(message = "Email ID must be specified.")
    private String email;

    @Column(name = "mobile", nullable = false, unique = true)
    @NotNull(message = "Mobile must be specified.")
    private String mobile;
}
