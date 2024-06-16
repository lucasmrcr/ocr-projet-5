package com.openclassrooms.starterjwt.controllers;

import com.openclassrooms.starterjwt.YogaAppMvcTestFramework;
import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class TeacherControllerTest extends YogaAppMvcTestFramework {

    @Autowired
    private TeacherRepository teacherRepository;

    @AfterEach
    public void cleanUp() {
        teacherRepository.deleteAll();
    }

    @Test
    public void testFindByIdInvalidInteger() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher/invalid")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testFindByIdNotFound() throws Exception {
        // Given
        // When
        mockMvc.perform(get("/api/teacher/2")
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    public void testFindByIdValid() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacherRepository.save(teacher);

        // When
        mockMvc.perform(get("/api/teacher/" + teacher.getId())
                        .header("Authorization", "Bearer " + getAdminAccessToken()))
                // Then
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAllTeachers() throws Exception {
        // Given
        Teacher teacher = new Teacher();
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacherRepository.save(teacher);

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
