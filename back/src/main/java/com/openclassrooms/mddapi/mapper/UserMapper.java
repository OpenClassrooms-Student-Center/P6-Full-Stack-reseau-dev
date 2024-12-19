package com.openclassrooms.mddapi.mapper;


import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserDto toDto(User user);
    User toEntity(UserDto userDto);

}
