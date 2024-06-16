package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.UserDto;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    public void testToDtoValid() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .admin(true)
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .createdAt(now)
                .updatedAt(now)
                .build();

        // When
        UserDto dto = userMapper.toDto(user);

        // Then
        assertEquals(1, dto.getId());
        assertEquals("user@example.com", dto.getEmail());
        assertTrue(dto.isAdmin());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("password", dto.getPassword());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    public void testToDtosValid() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        User user = User.builder()
                .id(1L)
                .email("user@example.com")
                .admin(true)
                .firstName("John")
                .lastName("Doe")
                .password("password")
                .createdAt(now)
                .updatedAt(now)
                .build();
        List<User> usersToDto = new ArrayList<>();
        usersToDto.add(user);

        // When
        List<UserDto> dtos = userMapper.toDto(usersToDto);
        UserDto dto = dtos.get(0);

        // Then
        assertEquals(1, dtos.size());
        assertEquals(1, dto.getId());
        assertEquals("user@example.com", dto.getEmail());
        assertTrue(dto.isAdmin());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
        assertEquals("password", dto.getPassword());
        assertEquals(now, dto.getCreatedAt());
        assertEquals(now, dto.getUpdatedAt());
    }

    @Test
    public void testToEntityValid() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(
                1L,
                "user@example.com",
                "Doe",
                "John",
                false,
                "password",
                now,
                now
        );

        // When
        User user = userMapper.toEntity(dto);

        // Then
        assertEquals(1, user.getId());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertFalse(user.isAdmin());
        assertEquals("password", user.getPassword());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

    @Test
    public void testToEntitiesValid() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        UserDto dto = new UserDto(
                1L,
                "user@example.com",
                "Doe",
                "John",
                false,
                "password",
                now,
                now
        );
        List<UserDto> dtos = new ArrayList<>();
        dtos.add(dto);

        // When
        List<User> users = userMapper.toEntity(dtos);
        User user = users.get(0);


        // Then
        assertEquals(1, user.getId());
        assertEquals("user@example.com", user.getEmail());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertFalse(user.isAdmin());
        assertEquals("password", user.getPassword());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }
}
