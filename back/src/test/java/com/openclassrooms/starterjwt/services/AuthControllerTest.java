package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.controllers.AuthController;
import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.payload.request.LoginRequest;
import com.openclassrooms.starterjwt.payload.request.SignupRequest;
import com.openclassrooms.starterjwt.payload.response.JwtResponse;
import com.openclassrooms.starterjwt.payload.response.MessageResponse;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class AuthControllerTest {

    @Autowired
    private AuthController authController;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setEmail("example@example.com");
        user.setPassword(passwordEncoder.encode("password"));
        user.setAdmin(false);
        user.setFirstName("John");
        user.setLastName("Doe");
        userRepository.save(user);
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void testAuthenticateCorrectUser() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("password");

        // When
        ResponseEntity<?> responseEntity = authController.authenticateUser(loginRequest);
        JwtResponse responseBody = (JwtResponse) responseEntity.getBody();

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseBody);
        assertEquals("example@example.com", responseBody.getUsername());
        assertEquals("John", responseBody.getFirstName());
        assertEquals("Doe", responseBody.getLastName());
        assertEquals(false, responseBody.getAdmin());
    }

    @Test
    public void testAuthenticateWithBadCredentials() {
        // Given
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail("example@example.com");
        loginRequest.setPassword("");

        // When
        Executable authenticateUser = () -> authController.authenticateUser(loginRequest);

        // Then
        assertThrows(BadCredentialsException.class, authenticateUser);
    }

    @Test
    public void  testRegisterValidUser() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("password");
        signupRequest.setEmail("not-existing@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");

        // When
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        MessageResponse responseBody = (MessageResponse) responseEntity.getBody();

        // Then
        assertEquals(200, responseEntity.getStatusCodeValue());
        assertNotNull(responseBody);
        assertEquals("User registered successfully!", responseBody.getMessage());
    }

    @Test
    public void  testRegisterExistingUser() {
        // Given
        SignupRequest signupRequest = new SignupRequest();
        signupRequest.setPassword("password");
        signupRequest.setEmail("example@example.com");
        signupRequest.setFirstName("John");
        signupRequest.setLastName("Doe");

        // When
        ResponseEntity<?> responseEntity = authController.registerUser(signupRequest);
        MessageResponse responseBody = (MessageResponse) responseEntity.getBody();

        // Then
        assertEquals(400, responseEntity.getStatusCodeValue());
        assertNotNull(responseBody);
        assertEquals("Error: Email is already taken!", responseBody.getMessage());
    }

}
