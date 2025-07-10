package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.type.NumericBooleanConverter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "user", schema = "crm",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email_id"),
            @UniqueConstraint(columnNames = "mobile_number"),
    })
@Builder
@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="userSeq", sequenceName = "USER_SEQ", allocationSize = 1, schema = "crm")
public class User implements Serializable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "userSeq")
    private Long id;

    @Column(name = "full_name", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Full Name must be specified.")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "Full Name must be between 2 and 50 characters")
    private String fullName;

    @Column(name = "business_name", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Business Name must be specified.")
    @jakarta.validation.constraints.Size(min = 2, max = 100, message = "Business Name must be between 2 and 100 characters")
    private String businessName;

    @Column(name = "email_id", nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(message = "Email ID must be specified.")
    @jakarta.validation.constraints.Email(message = "Email should be valid")
    private String email;

    @Column(name = "mobile_number", nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(message = "Mobile must be specified.")
    @jakarta.validation.constraints.Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Mobile number should be valid")
    private String mobile;

    @Column(name = "password", nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(message = "Password must be specified.")
    @jakarta.validation.constraints.Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @Column(name = "country", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Country must be specified.")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "Country name must be between 2 and 50 characters")
    private String country;

    @Column(name = "state", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "State must be specified.")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "State name must be between 2 and 50 characters")
    private String state;

    @Column(name = "city", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "City must be specified.")
    @jakarta.validation.constraints.Size(min = 2, max = 50, message = "City name must be between 2 and 50 characters")
    private String city;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    @Convert(converter = NumericBooleanConverter.class)
    @Column(name = "enabled", nullable = false, columnDefinition = "INT2")
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_roles",
            schema = "crm",
            joinColumns = @JoinColumn(
                    name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "id")
    )
    private Set<Role> roles = new HashSet<>();
}
