package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * Tracks all activities and status changes on a lead.
 * Provides an audit trail of lead progression through the sales pipeline.
 */
@Entity
@Table(name = "lead_activity", schema = "crm")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "leadActivitySeq", sequenceName = "LEAD_ACTIVITY_SEQ", allocationSize = 1, schema = "crm")
public class LeadActivity implements Serializable {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leadActivitySeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performed_by")
    private User performedBy;

    @Column(name = "activity_type", nullable = false)
    private String activityType; // STATUS_CHANGE, ASSIGNMENT, NOTE_ADDED, EMAIL_SENT, etc.

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "old_value")
    private String oldValue;

    @Column(name = "new_value")
    private String newValue;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeadActivity)) return false;
        return id != null && id.equals(((LeadActivity) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
