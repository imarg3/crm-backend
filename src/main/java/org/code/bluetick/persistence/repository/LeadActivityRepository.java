package org.code.bluetick.persistence.repository;

import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.LeadActivity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;

/**
 * Repository for LeadActivity entities.
 * Tracks audit trail of all lead-related activities.
 */
@Repository
public interface LeadActivityRepository extends JpaRepository<LeadActivity, Long> {

    /**
     * Find all activities for a specific lead, ordered by creation date descending.
     */
    List<LeadActivity> findByLeadOrderByCreatedAtDesc(Lead lead);

    /**
     * Find activities by type for analytics.
     */
    List<LeadActivity> findByActivityTypeOrderByCreatedAtDesc(String activityType);

    /**
     * Find recent activities for a lead (last N days).
     */
    List<LeadActivity> findByLeadAndCreatedAtAfterOrderByCreatedAtDesc(
        Lead lead,
        Instant since
    );

    /**
     * Find all activities performed by a specific user.
     */
    List<LeadActivity> findByPerformedByIdOrderByCreatedAtDesc(Long userId);
}
