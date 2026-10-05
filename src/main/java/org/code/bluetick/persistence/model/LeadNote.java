package org.code.bluetick.persistence.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.Instant;

/**
 * Represents notes/comments on a lead for tracking communication history.
 * Essential CRM feature for maintaining customer interaction records.
 */
@Entity
@Table(name = "lead_note", schema = "crm")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@SequenceGenerator(name = "leadNoteSeq", sequenceName = "LEAD_NOTE_SEQ", allocationSize = 1, schema = "crm")
public class LeadNote implements Serializable {

    @Id
    @Column(unique = true, nullable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "leadNoteSeq")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lead_id", nullable = false)
    private Lead lead;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by", nullable = false)
    private User createdBy;

    @Column(name = "note_content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "note_type")
    private String noteType; // CALL, EMAIL, MEETING, GENERAL

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LeadNote)) return false;
        return id != null && id.equals(((LeadNote) o).getId());
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
