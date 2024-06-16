package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.TeacherDto;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeacherMapperTest {

    private final TeacherMapper teacherMapper = Mappers.getMapper(TeacherMapper.class);

    @Test
    public void testToDtoValid() {
        // Given
        Teacher teacher = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();

        // When
        TeacherDto dto = teacherMapper.toDto(teacher);

        // Then
        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    public void testToDtosValid() {
        // Given
        Teacher teacher = Teacher.builder().id(1L).firstName("John").lastName("Doe").build();
        List<Teacher> teachers = new ArrayList<>();
        teachers.add(teacher);

        // When
        List<TeacherDto> dtos = teacherMapper.toDto(teachers);
        TeacherDto dto = dtos.get(0);

        // Then
        assertEquals(1, dtos.size());
        assertEquals(1, dto.getId());
        assertEquals("John", dto.getFirstName());
        assertEquals("Doe", dto.getLastName());
    }

    @Test
    public void testToEntityValid() {
        // Given
        TeacherDto dto = new TeacherDto(1L, "John", "Doe", null, null);

        // When
        Teacher teacher = teacherMapper.toEntity(dto);

        // Then
        assertEquals(1, teacher.getId());
        assertEquals("John", teacher.getLastName());
        assertEquals("Doe", teacher.getFirstName());
    }

    @Test
    public void testToEntitiesValid() {
        // Given
        TeacherDto dto = new TeacherDto(1L, "John", "Doe", null, null);
        List<TeacherDto> dtos = new ArrayList<>();
        dtos.add(dto);

        // When
        List<Teacher> teachers = teacherMapper.toEntity(dtos);
        Teacher teacher = teachers.get(0);

        // Then
        assertEquals(1, teachers.size());
        assertEquals(1, teacher.getId());
        assertEquals("John", teacher.getLastName());
        assertEquals("Doe", teacher.getFirstName());
    }

}
