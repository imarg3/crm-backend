package org.code.bluetick.web.mapstruct.mapper;

import org.code.bluetick.persistence.model.User;
import org.code.bluetick.web.mapstruct.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MapStructMapper {
    UserDto userToUserDto(User user);

    User userDtoToUser(UserDto userDto);

    // LeadDto leadToLeadDto(Lead lead);

    // List<LeadDto> leadListToLeadDtoList(List<Lead> lead);
}
