package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "customer", schema = "crm",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "email"),
            @UniqueConstraint(columnNames = "mobile")
    })
@Getter@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="customerSeq", sequenceName = "CUSTOMER_SEQ", allocationSize = 1, schema = "crm")
public class Customer implements Serializable {
    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customerSeq")
    private Long id;

    @Column(name = "name", nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Name must be specified")
    @jakarta.validation.constraints.Size(min = 2, max = 100, message = "Name must be between 2 and 100 characters")
    private String name;

    @Column(name = "email", nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(message = "Email must be specified")
    @jakarta.validation.constraints.Email(message = "Email should be valid")
    private String email;

    @Column(name = "mobile", nullable = false, unique = true)
    @jakarta.validation.constraints.NotNull(message = "Mobile must be specified")
    @jakarta.validation.constraints.Pattern(regexp = "^[+]?[0-9]{10,15}$", message = "Mobile number should be valid")
    private String mobile;

    @Column(name="birth_date", nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @jakarta.validation.constraints.NotNull(message = "Birth date must be specified")
    @jakarta.validation.constraints.Past(message = "Birth date must be in the past")
    private LocalDate birthDate;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    @ToString.Exclude
    @Singular
    @OneToMany(mappedBy = "customer", cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
    })
    private List<Lead> leads;

    // convenience methods for bi-directional relationship
    public void addLead(Lead lead) {
        if(leads == null) {
            leads = new ArrayList<>();
        }
        // The below 2 statements are for creating bi-directional link
        leads.add(lead);
        lead.setCustomer(this);
    }

    public void removeLead(Lead lead) {
        if(leads != null) {
            leads.remove(lead);
            lead.setCustomer(null);
        }
    }
}
