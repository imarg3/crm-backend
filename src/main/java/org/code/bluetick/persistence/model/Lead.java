package org.code.bluetick.persistence.model;

import io.hypersistence.utils.hibernate.type.array.EnumArrayType;
import io.hypersistence.utils.hibernate.type.array.internal.AbstractArrayType;
import jakarta.persistence.*;
import lombok.*;
import org.code.bluetick.enums.LeadStatus;
import org.code.bluetick.enums.Services;
import org.hibernate.annotations.ColumnTransformer;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Table(name = "lead", schema = "crm",
    uniqueConstraints = {
        @UniqueConstraint(columnNames = "lead_id")
    })
@Builder
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name="leadSeq", sequenceName = "LEAD_SEQ", allocationSize = 1)
public class Lead implements Serializable {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leadSeq")
    private Long id;

    @Column(name="lead_id", unique = true, nullable = false)
    @jakarta.validation.constraints.NotNull(message = "Lead ID must be specified")
    @jakarta.validation.constraints.Size(min = 5, max = 50, message = "Lead ID must be between 5 and 50 characters")
    private String leadId;

    @ToString.Exclude
    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST, CascadeType.MERGE, CascadeType.DETACH, CascadeType.REFRESH
    })
    @JoinColumn(name = "customer_id")
    @jakarta.validation.constraints.NotNull(message = "Customer must be specified")
    private Customer customer;

    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "travel_detail_id")
    @jakarta.validation.constraints.NotNull(message = "Travel details must be specified")
    private TravelDetail travelDetail;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", columnDefinition = "crm.status", nullable = false)
    @ColumnTransformer(write = "?::crm.lead_status")
    @jakarta.validation.constraints.NotNull(message = "Status must be specified")
    private LeadStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "services", columnDefinition = "crm.services[]", nullable = false)
    @Type(value = EnumArrayType.class, parameters = @org.hibernate.annotations.Parameter(
            name = AbstractArrayType.SQL_ARRAY_TYPE,
            value = "crm.services"
    ))
    @jakarta.validation.constraints.NotNull(message = "Services must be specified")
    @jakarta.validation.constraints.Size(min = 1, message = "At least one service must be selected")
    private Services[] services;

    @CreatedDate
    @Column(name="created_at", nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(name="updated_at", nullable = false)
    private Instant updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Lead )) return false;
        return id != null && id.equals(((Lead) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}