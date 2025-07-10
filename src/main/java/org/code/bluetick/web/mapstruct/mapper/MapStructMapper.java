package org.code.bluetick.web.mapstruct.mapper;

import org.code.bluetick.persistence.model.*;
import org.code.bluetick.web.mapstruct.dto.*;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {
    // User mappings
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto userDto);
    UserResponseDto userToUserResponseDto(User user);

    // Customer mappings
    Customer customerDtoToCustomer(CustomerDto customerDto);
    CustomerResponseDto customerToCustomerResponseDto(Customer customer);

    // Lead mappings
    Lead leadDtoToLead(LeadDto leadDto);
    LeadResponseDto leadToLeadResponseDto(Lead lead);

    // Travel Detail mappings
    TravelDetail travelDetailDtoToTravelDetail(TravelDetailDto travelDetailDto);
    TravelDetailResponseDto travelDetailToTravelDetailResponseDto(TravelDetail travelDetail);

    // Traveller mappings
    Traveller travellerDtoToTraveller(TravellerDto travellerDto);
    TravellerResponseDto travellerToTravellerResponseDto(Traveller traveller);

    // List mappings
    List<CustomerResponseDto> customerListToCustomerResponseDtoList(List<Customer> customers);
    List<LeadResponseDto> leadListToLeadResponseDtoList(List<Lead> leads);
    List<TravellerResponseDto> travellerListToTravellerResponseDtoList(List<Traveller> travellers);

    @AfterMapping
    default void setTravelDetail(@MappingTarget TravelDetail travelDetail) {
        Optional.ofNullable(travelDetail.getTravellers())
                .ifPresent(tr -> tr.forEach(traveller -> traveller.setTravelDetail(travelDetail)));
    }
}
