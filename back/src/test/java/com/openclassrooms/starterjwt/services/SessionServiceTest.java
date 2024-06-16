package com.openclassrooms.starterjwt.services;

import com.openclassrooms.starterjwt.models.Session;
import com.openclassrooms.starterjwt.repository.SessionRepository;
import com.openclassrooms.starterjwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SessionServiceTest {

    @Mock
    private SessionRepository sessionRepository;

    @Mock
    private UserRepository userRepository;

    private SessionService sessionService;

    @BeforeEach
    public void setUp() {
        sessionService = new SessionService(sessionRepository, userRepository);
    }

    @Test
    public void testCreateSession() {
        // Given
        when(sessionRepository.save(any(Session.class)))
                .thenReturn(Session.builder().name("Session 1").build());

        // When
        Session session = sessionService.create(new Session());

        // Then
        verify(sessionRepository).save(any(Session.class));
        assertEquals("Session 1", session.getName());
    }

    @Test
    public void testDeleteSession() {
        // Given
        doNothing().when(sessionRepository).deleteById(any(Long.class));

        // When
        sessionService.delete(1L);

        // Then
        verify(sessionRepository).deleteById(any(Long.class));
    }

    @Test
    public void testFindAllSessions() {
        // Given
        Session session = Session.builder().id(1L).name("Session 1").build();
        List<Session> shouldFindSessions = new ArrayList<>();
        shouldFindSessions.add(session);

        when(sessionRepository.findAll()).thenReturn(shouldFindSessions);

        // When
        List<Session> all = sessionService.findAll();

        // Then
        verify(sessionRepository).findAll();
        assertEquals(shouldFindSessions, all);
    }

    @Test
    public void testFindSessionById() {
        // Given
        Session sessionToFind = Session.builder().id(1L).name("Session 1").build();

        when(sessionRepository.findById(anyLong())).thenReturn(Optional.of(sessionToFind));

        // When
        Session session = sessionService.getById(1L);

        // Then
        verify(sessionRepository).findById(anyLong());
        assertEquals(sessionToFind, session);
    }

}
