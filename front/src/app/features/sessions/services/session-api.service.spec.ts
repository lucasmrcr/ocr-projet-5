import {HttpClient, HttpClientModule} from '@angular/common/http';
import { TestBed } from '@angular/core/testing';
import { expect } from '@jest/globals';

import { SessionApiService } from './session-api.service';
import spyOn = jest.spyOn;
import {of} from "rxjs";

describe('SessionsService', () => {
  let service: SessionApiService;
  let httpClient: HttpClient;

  const session = {
    id: 1,
    name: 'Session 1',
    description: 'Description 1',
    date: new Date(),
    teacher_id: 1,
    users: []
  };

  const httpClientMock = {
    get: jest.fn(),
    post: jest.fn(),
    put: jest.fn(),
    delete: jest.fn()
  };

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports:[
        HttpClientModule
      ],
      providers: [
        { provide: HttpClient, useValue: httpClientMock }
      ]
    });
    service = TestBed.inject(SessionApiService);
    httpClient = TestBed.inject(HttpClient);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  it('should get all sessions', () => {
    // Given
    spyOn(httpClientMock, 'get').mockReturnValue(of([session]));

    //When
    service.all().subscribe((sessions) => {
    // Then
      expect(sessions).toEqual([session]);
    });
    expect(httpClient.get).toHaveBeenCalledWith('api/session');
  })

  it('should get session detail', () => {
    // Given
    spyOn(httpClientMock, 'get').mockReturnValue(of(session));

    //When
    service.detail('1').subscribe((session) => {
    // Then
      expect(session).toEqual(session);
    });
    expect(httpClient.get).toHaveBeenCalledWith('api/session/1');
  })

  it('should delete session', () => {
    // Given
    spyOn(httpClientMock, 'delete').mockReturnValue(of({}));

    //When
    service.delete('1').subscribe((session) => {
    // Then
      expect(session).toEqual({});
    });
    expect(httpClient.delete).toHaveBeenCalledWith('api/session/1');
  })

  it('should create session', () => {
    // Given
    spyOn(httpClientMock, 'post').mockReturnValue(of(session));

    //When
    service.create(session).subscribe((session) => {
    // Then
      expect(session).toEqual(session);
    });
    expect(httpClient.post).toHaveBeenCalledWith('api/session', session);
  })

  it('should update session', () => {
    // Given
    spyOn(httpClientMock, 'put').mockReturnValue(of(session));

    //When
    service.update('1', session).subscribe((session) => {
    // Then
      expect(session).toEqual(session);
    });
    expect(httpClient.put).toHaveBeenCalledWith('api/session/1', session);
  })

  it('should participate in session', () => {
    // Given
    spyOn(httpClientMock, 'post').mockReturnValue(of());

    //When
    service.participate('1', '1').subscribe((session) => {
    // Then
      expect(session).toEqual({});
    });
    expect(httpClient.post).toHaveBeenCalledWith('api/session/1/participate/1', null);
  })

  it('should unparticipate in session', () => {
    // Given
    spyOn(httpClientMock, 'delete').mockReturnValue(of());

    //When
    service.unParticipate('1', '1').subscribe((session) => {
    // Then
      expect(session).toEqual({});
    });
    expect(httpClient.delete).toHaveBeenCalledWith('api/session/1/participate/1');
  })

});
