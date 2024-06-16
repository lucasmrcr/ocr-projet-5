package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.AsAdminTestFramework;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTest extends AsAdminTestFramework {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testFindByIdNotExistingUser() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/user/10")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isNotFound());

    }

    @Test
    public void testFindByIdNotValidInteger() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/user/dix")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testFindByIdExistingUser() throws Exception {
        // Given
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("example@example.com");
        user.setAdmin(false);
        userRepository.save(user);

        // When
        mockMvc.perform(get("/api/user/" + user.getId())
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(user.getId()))
                .andExpect(jsonPath("$.email").value("example@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    public void testDeleteNotExistingUser() throws Exception {
        // Given
        // When
        mockMvc.perform(delete("/api/user/10")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isNotFound());

    }

    @Test
    public void testDeleteNotValidInteger() throws Exception {
        // Given
        // When
        mockMvc.perform(delete("/api/user/dix")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isBadRequest());

    }

    @Test
    public void testDeleteUnauthorizedExistingUser() throws Exception {
        // Given
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("example@example.com");
        user.setAdmin(false);
        userRepository.save(user);

        // When
        mockMvc.perform(delete("/api/user/" + user.getId())
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                //Then
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testDeleteValidExistingUser() throws Exception {
        // Given
        User user = new User();
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("example@example.com");
        user.setAdmin(false);
        user.setPassword(passwordEncoder.encode("password"));
        userRepository.save(user);
        String token = authenticate(user.getEmail(), "password");

        // When
        mockMvc.perform(delete("/api/user/" + user.getId())
                        .header("Authorization", "Bearer " + token))
                //Then
                .andExpect(status().isOk());
        assertFalse(userRepository.findById(user.getId()).isPresent());
    }
}
