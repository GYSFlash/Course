package com.hotel.mapper;

import com.hotel.dto.UserDTO;
import com.hotel.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserNapper {
    User toUser(UserDTO userDto);
}
