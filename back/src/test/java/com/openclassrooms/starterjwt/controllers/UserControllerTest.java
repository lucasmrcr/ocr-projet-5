package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.YogaAppMvcTestFramework;
import com.openclassrooms.starterjwt.models.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends YogaAppMvcTestFramework {

    @AfterEach
    public void cleanUp() {
        getUserRepository().deleteAll();
    }

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
        user = getUserRepository().save(user);

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
        getUserRepository().save(user);

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
        user.setPassword(getPasswordEncoder().encode("password"));
        getUserRepository().save(user);

        String token = authenticate(user.getEmail(), "password");

        // When
        mockMvc.perform(delete("/api/user/" + user.getId())
                        .header("Authorization", "Bearer " + token))
                //Then
                .andExpect(status().isOk());
        assertFalse(getUserRepository().findById(user.getId()).isPresent());
    }
}
