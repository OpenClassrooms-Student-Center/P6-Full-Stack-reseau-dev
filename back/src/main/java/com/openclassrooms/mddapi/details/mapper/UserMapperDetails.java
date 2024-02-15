package com.openclassrooms.mddapi.details.mapper;

import com.openclassrooms.mddapi.core.usecases.EntityMapper;
import com.openclassrooms.mddapi.details.models.UserEntity;
import com.openclassrooms.mddapi.core.domain.models.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapperDetails extends EntityMapper<UserEntity, User> {
}
