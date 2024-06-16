package com.openclassrooms.starterjwt;

import com.openclassrooms.starterjwt.models.User;
import com.openclassrooms.starterjwt.repository.UserRepository;
import com.openclassrooms.starterjwt.security.jwt.JwtUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class AsAdminTestFramework {

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;

    private String adminAccessToken;

    @BeforeEach
    public void setUp() {
        User admin = new User();
        admin.setEmail("admin@example.com");
        admin.setLastName("Doe");
        admin.setFirstName("John");
        admin.setAdmin(true);
        admin.setPassword(passwordEncoder.encode("password"));
        userRepository.save(admin);
        adminAccessToken = authenticate(admin.getEmail(), "password");
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    protected String getAdminAccessToken() {
        return adminAccessToken;
    }

    protected String authenticate(String email, String password) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(email, password));

        return jwtUtils.generateJwtToken(authentication);
    }
}
