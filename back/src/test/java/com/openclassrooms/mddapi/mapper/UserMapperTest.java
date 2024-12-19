package com.openclassrooms.mddapi.mapper;

import com.openclassrooms.mddapi.dto.UserDto;
import com.openclassrooms.mddapi.models.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserMapperTest {
    private UserMapper userMapper = Mappers.getMapper(UserMapper.class);


    private User user;
    private UserDto userDto;

    @BeforeEach
    public void setUp() {
        user = User.builder().id(1L).email("test@gmail.com").username("Joseph").password("pwd").build();
        userDto = UserDto.builder().email("test@gmail.com").username("Joseph").build();

    }
    @Test
    public void givenUser_whenMapUserDto_thenReturnUserDto() {
        Assertions.assertEquals(userDto.getEmail(),user.getEmail());
    }
}
