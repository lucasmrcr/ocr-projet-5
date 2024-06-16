package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.AsAdminTestFramework;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class TeacherControllerTest extends AsAdminTestFramework {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    @BeforeEach
    public void setUp() {
        super.setUp();
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacherRepository.save(teacher);
    }

    @AfterEach
    public void tearDown() {
        super.tearDown();
        teacherRepository.deleteAll();
    }

    @Test
    public void findByIdInvalidInteger() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher/invalid")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void findByIdNotFound() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher/2")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void findByIdValid() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher/1")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllTeachers() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].firstName").value("John"))
                .andExpect(jsonPath("$[0].lastName").value("Doe"));
    }
}
