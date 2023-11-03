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
    //@NotNull(message = "Full Name must be specified.")
    private String fullName;

    @Column(name = "business_name", nullable = false)
    //@NotNull(message = "Business Name must be specified.")
    private String businessName;

    @Column(name = "email_id", nullable = false, unique = true)
    //@NotNull(message = "Email ID must be specified.")
    private String email;

    @Column(name = "mobile_number", nullable = false, unique = true)
    //@NotNull(message = "Mobile must be specified.")
    private String mobile;

    @Column(name = "password", nullable = false, unique = true)
    //@NotNull(message = "Password must be specified.")
    private String password;

    @Column(name = "country", nullable = false)
    //@NotNull(message = "Country must be specified.")
    private String country;

    @Column(name = "state", nullable = false)
    //@NotNull(message = "State must be specified.")
    private String state;

    @Column(name = "city", nullable = false)
    //@NotNull(message = "City must be specified.")
    private String city;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    //@NotNull(message = "Created date must be specified.")
    private Instant createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    //@NotNull(message = "Updated date must be specified.")
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
