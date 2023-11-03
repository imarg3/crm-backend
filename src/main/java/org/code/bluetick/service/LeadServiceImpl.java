package org.code.bluetick.service;

import lombok.RequiredArgsConstructor;
import org.code.bluetick.enums.LeadStatus;
import org.code.bluetick.persistence.model.Customer;
import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.TravelDetail;
import org.code.bluetick.persistence.model.Traveller;
import org.code.bluetick.persistence.repository.LeadRepository;
import org.code.bluetick.utils.LeadUtils;
import org.code.bluetick.web.exception.LeadAlreadyExistException;
import org.code.bluetick.web.exception.LeadNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService{
    private final LeadRepository leadRepository;

    @Override
    public Lead createNewLead(final Lead lead) {
        System.out.println("Lead data :: " + lead);
        Assert.notNull(lead, "Lead must not be null");
        if(leadAlreadyExists(lead.getLeadId())) {
            throw new LeadAlreadyExistException("There is already a lead with a lead id: " + lead.getLeadId());
        }

        System.out.println("Customer Info :: " + lead.getCustomer());

        /*
        if (customerRepository.findByEmail(lead.getCustomer().getEmail()).isEmpty() &&

            customerRepository.findByMobile(lead.getCustomer().getMobile()).isEmpty()){
            customerRepository.save(lead.getCustomer());
        }
        */
        System.out.println("Travel Detail Info :: " + lead.getTravelDetail());
        System.out.println("Travellers Info :: " + lead.getTravelDetail().getTravellers());

        final String leadId = LeadUtils.generateLeadID();
        System.out.println("Lead ID :: " + leadId);
        lead.setLeadId(leadId);
        lead.setStatus(LeadStatus.QUOTE_SENT);

        Customer customer = lead.getCustomer();
        System.out.println("Customer get :: " + customer);
        TravelDetail travelDetail = lead.getTravelDetail();
        System.out.println("Travel Detail get :: " + travelDetail);
        List<Traveller> travellers = lead.getTravelDetail().getTravellers();
        travellers.forEach(traveller -> traveller.setTravelDetail(travelDetail));

        return leadRepository.save(lead);
    }

    @Override
    public Lead findLeadByLeadId(String leadId) {
        Optional<Lead> optionalLead = leadRepository.findByLeadId(leadId);
        if(optionalLead.isPresent()) {
            return optionalLead.get();
        } else {
            throw new LeadNotFoundException("No lead found with lead id: " + leadId);
        }
    }

    public Page<Lead> getAllLeads(Pageable pageable) {
        return leadRepository.findAll(
                PageRequest.of(
                        pageable.getPageNumber(),
                        pageable.getPageSize(),
                        pageable.getSortOr(Sort.by(Sort.Direction.ASC, "id"))
                ));
    }

    private boolean leadAlreadyExists(final String leadId) {
        return leadRepository.findByLeadId(leadId).isPresent();
    }
}