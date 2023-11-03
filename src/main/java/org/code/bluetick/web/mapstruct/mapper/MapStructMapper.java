package org.code.bluetick.web.mapstruct.mapper;

import org.code.bluetick.persistence.model.Lead;
import org.code.bluetick.persistence.model.TravelDetail;
import org.code.bluetick.persistence.model.Traveller;
import org.code.bluetick.persistence.model.User;
import org.code.bluetick.web.mapstruct.dto.LeadDto;
import org.code.bluetick.web.mapstruct.dto.TravelDetailDto;
import org.code.bluetick.web.mapstruct.dto.TravellerDto;
import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.mapstruct.*;

import java.util.List;
import java.util.Optional;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MapStructMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    Lead leadDtoToLead(LeadDto leadDto);

    TravelDetail travelDetailDtoToTravelDetail(TravelDetailDto travelDetailDto);

    Traveller travellerDtoToTraveller(TravellerDto travellerDto);

    /*
    @Mapping(target = "travellers", qualifiedByName = "travellerDtoListToTravellerList")
   TravelDetail travelDetailDtoToTravelDetail(TravelDetailDto travelDetailDto);

    @IterableMapping(qualifiedByName = "travellerDtoToTraveller")
    @Named("travellerDtoListToTravellerList")
    List<Traveller> travellerDtoListToTravellerList(List<TravellerDto> list);

    @Named("travellerDtoToTraveller")
    Traveller travellerDtoToTraveller(TravellerDto travellerDto);

    @AfterMapping
    default void setTravelDetail(@MappingTarget TravelDetail travelDetail) {
        Optional.ofNullable(travelDetail.getTravellers())
                .ifPresent(tr -> tr.forEach(traveller -> traveller.setTravelDetail(travelDetail)));
    }

    TravelDetailDto travelDetailToTravelDetailDto(TravelDetail travelDetail);
    */

    // LeadDto leadToLeadDto(Lead lead);

    // List<LeadDto> leadListToLeadDtoList(List<Lead> lead);
}
