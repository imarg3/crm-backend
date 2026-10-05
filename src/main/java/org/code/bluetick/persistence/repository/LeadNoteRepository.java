package org.code.bluetick.persistence.repository;

import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.LeadNote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for LeadNote entities.
 * Manages notes/comments associated with leads.
 */
@Repository
public interface LeadNoteRepository extends JpaRepository<LeadNote, Long> {

    /**
     * Find all notes for a specific lead, ordered by creation date descending.
     */
    List<LeadNote> findByLeadOrderByCreatedAtDesc(Lead lead);

    /**
     * Find all notes created by a specific user.
     */
    List<LeadNote> findByCreatedByIdOrderByCreatedAtDesc(Long userId);

    /**
     * Count notes for a specific lead.
     */
    long countByLead(Lead lead);
}
