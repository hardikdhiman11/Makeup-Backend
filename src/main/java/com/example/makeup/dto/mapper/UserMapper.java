package com.example.makeup.dto.mapper;

import com.example.makeup.dto.UserDto;
import com.example.makeup.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto userToUserDto(User user);
    @Mapping(target = "id",ignore = true)
    @Mapping(target = "authorities",ignore = true)
    User userDtoToUser(UserDto userDto);
}
