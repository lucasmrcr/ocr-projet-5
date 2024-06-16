package com.openclassrooms.starterjwt.mapper;

import com.openclassrooms.starterjwt.dto.SessionDto;
import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.models.Teacher;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SessionMapperTest {

    private final SessionMapper sessionMapper = Mappers.getMapper(SessionMapper.class);

    @Test
    public void testToDto() {
        // Given

        Date date = new Date();
        Session session = Session.builder()
                .id(1L)
                .name("Session 1")
                .date(date)
                .users(new ArrayList<>())
                .description("Description")
                .build();

        // When
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(0, session.getUsers().size());
        assertEquals(session.getDescription(), sessionDto.getDescription());
    }

    @Test
    public void testToDtos() {
        // Given
        Date date = new Date();
        Session session = Session.builder()
                .id(1L)
                .name("Session 1")
                .date(date)
                .users(new ArrayList<>())
                .description("Description")
                .build();
        List<Session> sessionsToDtos = new ArrayList<>();
        sessionsToDtos.add(session);

        // When
        List<SessionDto> sessionDtos = sessionMapper.toDto(sessionsToDtos);
        SessionDto sessionDto = sessionMapper.toDto(session);

        // Then
        assertEquals(1, sessionDtos.size());
        assertEquals(session.getId(), sessionDto.getId());
        assertEquals(session.getName(), sessionDto.getName());
        assertEquals(session.getDate(), sessionDto.getDate());
        assertEquals(0, session.getUsers().size());
        assertEquals(session.getDescription(), sessionDto.getDescription());
    }

    @Test
    public void testToEntity() {
        // Given
        Date date = new Date();
        SessionDto sessionDto = new SessionDto(
                1L,
                "Session 1",
                date,
                null,
                "Description",
                new ArrayList<>(),
                null,
                null
        );

        // When
        Session session = sessionMapper.toEntity(sessionDto);

        // Then
        assertEquals(sessionDto.getId(), session.getId());
        assertEquals(sessionDto.getName(), session.getName());
        assertEquals(sessionDto.getDate(), session.getDate());
        assertEquals(0, sessionDto.getUsers().size());
        assertEquals(sessionDto.getDescription(), session.getDescription());
    }

    @Test
    public void testToEntities() {
        // Given
        Date date = new Date();
        SessionDto sessionDto = new SessionDto(
                1L,
                "Session 1",
                date,
                null,
                "Description",
                new ArrayList<>(),
                null,
                null
        );
        List<SessionDto> sessionDtos = new ArrayList<>();
        sessionDtos.add(sessionDto);

        // When
        List<Session> sessions = sessionMapper.toEntity(sessionDtos);

        // Then
        assertEquals(1, sessions.size());
        assertEquals(sessionDto.getId(), sessions.get(0).getId());
        assertEquals(sessionDto.getName(), sessions.get(0).getName());
        assertEquals(sessionDto.getDate(), sessions.get(0).getDate());
        assertEquals(0, sessionDto.getUsers().size());
        assertEquals(sessionDto.getDescription(), sessions.get(0).getDescription());
    }
}
