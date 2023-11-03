package org.code.bluetick.service;

import org.code.bluetick.persistence.model.Lead;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface LeadService {
    Lead createNewLead(Lead lead);

    Lead findLeadByLeadId(String leadId);
    Page<Lead> getAllLeads(Pageable pageable);
}
