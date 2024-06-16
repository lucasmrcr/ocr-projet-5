package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    public void setUp() {
        userService = new UserService(userRepository);
    }

    @Test
    public void deleteUserWorksFine() {
        // Given
        doNothing().when(userRepository).deleteById(1L);

        // When
        userService.delete(1L);

        // Then
        verify(userRepository).deleteById(1L);
    }

    @Test
    public void findUserWhenDoesNotExists() {
        // Given
        // When
        User user = userService.findById(1L);

        // Then
        verify(userRepository).findById(1L);
        assertNull(user);
    }

    @Test
    public void findUserWhenExists() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        User userToCreate = new User();
        userToCreate.setId(1L);
        userToCreate.setEmail("example@example.com");
        userToCreate.setAdmin(false);
        userToCreate.setPassword("password");
        userToCreate.setLastName("Doe");
        userToCreate.setFirstName("John");
        userToCreate.setCreatedAt(now);
        userToCreate.setUpdatedAt(now);
        when(userRepository.findById(1L)).thenReturn(Optional.of(userToCreate));

        // When
        User user = userService.findById(1L);

        // Then
        verify(userRepository).findById(1L);
        assertNotNull(user);
        assertEquals(1L, user.getId());
        assertEquals("example@example.com", user.getEmail());
        assertFalse(user.isAdmin());
        assertEquals("password", user.getPassword());
        assertEquals("Doe", user.getLastName());
        assertEquals("John", user.getFirstName());
        assertEquals(now, user.getCreatedAt());
        assertEquals(now, user.getUpdatedAt());
    }

}
