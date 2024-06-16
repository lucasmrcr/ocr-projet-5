package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Teacher;
import com.openclassrooms.starterjwt.repository.TeacherRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;
    private TeacherService teacherService;

    @BeforeEach
    public void setUp() {
        teacherService = new TeacherService(teacherRepository);
    }

    @Test
    public void testFindAll() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = Teacher.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .createdAt(now)
                .updatedAt(now)
                .build();

        List<Teacher> teachersToLoad = new ArrayList<>();
        teachersToLoad.add(teacher);

        when(teacherRepository.findAll()).thenReturn(teachersToLoad);

        // When
        List<Teacher> teachersFromService = teacherService.findAll();

        // Then
        assertEquals(1, teachersFromService.size());
        assertEquals(1L, teachersFromService.get(0).getId());
        assertEquals("John", teachersFromService.get(0).getFirstName());
        assertEquals("Doe", teachersFromService.get(0).getLastName());
        assertEquals(now, teachersFromService.get(0).getCreatedAt());
        assertEquals(now, teachersFromService.get(0).getUpdatedAt());
    }

    @Test
    public void testFindById() {
        // Given
        LocalDateTime now = LocalDateTime.now();
        Teacher teacher = new Teacher();
        teacher.setId(1L);
        teacher.setFirstName("John");
        teacher.setLastName("Doe");
        teacher.setCreatedAt(now);
        teacher.setUpdatedAt(now);

        when(teacherRepository.findById(1L)).thenReturn(java.util.Optional.of(teacher));

        // When
        Teacher teacherFromService = teacherService.findById(1L);

        // Then
        assertEquals(1L, teacherFromService.getId());
        assertEquals("John", teacherFromService.getFirstName());
        assertEquals("Doe", teacherFromService.getLastName());
        assertEquals(now, teacherFromService.getCreatedAt());
        assertEquals(now, teacherFromService.getUpdatedAt());
    }

}
