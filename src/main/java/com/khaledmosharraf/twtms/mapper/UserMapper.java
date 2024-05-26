package com.khaledmosharraf.twtms.mapper;

import com.khaledmosharraf.twtms.dto.UserDTO;
import com.khaledmosharraf.twtms.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toModel(UserDTO userDTO);
    UserDTO toDTO(User user);

}
