package org.code.bluetick.persistence.repository;

import org.code.bluetick.persistence.model.Lead;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LeadRepository extends JpaRepository<Lead, Long> {
    @Query("select lid from Lead lid where lid.leadId = ?1")
    Optional<Lead> findByLeadId(String leadId);
}