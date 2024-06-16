package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.YogaAppMvcTestFramework;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends YogaAppMvcTestFramework {

    @AfterEach
    public void cleanUp() {
        getUserRepository().deleteAll();
    }

    @Test
    public void testAuthenticateCorrectUser() throws Exception {
        // Given
        User user = new User();
        user.setEmail("example@example.com");
        user.setPassword(getPasswordEncoder().encode("password"));
        user.setAdmin(false);
        user.setFirstName("John");
        user.setLastName("Doe");
        getUserRepository().save(user);

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("password");

        // When
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(loginRequest)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("example@example.com"))
                .andExpect(jsonPath("$.firstName").value("John"))
                .andExpect(jsonPath("$.lastName").value("Doe"))
                .andExpect(jsonPath("$.admin").value(false));
    }

    @Test
    public void testAuthenticateWithBadCredentials() throws Exception {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("");

        // When
        mockMvc.perform(post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(loginRequest)))

                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testRegisterValidUser() throws Exception {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("password");
        signupRequest.setEmail("not-existing@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");

        // When
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(signupRequest)))

                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("User registered successfully!"));
    }

    @Test
    public void testRegisterExistingUser() throws Exception {
        // Given
        User user = new User();
        user.setEmail("example@example.com");
        user.setPassword(getPasswordEncoder().encode("password"));
        user.setAdmin(false);
        user.setFirstName("John");
        user.setLastName("Doe");
        getUserRepository().save(user);

        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("password");
        signupRequest.setEmail("example@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");

        // When
        mockMvc.perform(post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(getObjectMapper().writeValueAsString(signupRequest)))

                // Then
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Error: Email is already taken!"));
    }

}
