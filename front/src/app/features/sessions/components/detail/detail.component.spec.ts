import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatSnackBar, MatSnackBarModule} from '@angular/material/snack-bar';
import {RouterTestingModule} from '@angular/router/testing';
import {HttpClientModule} from '@angular/common/http';
import {of} from 'rxjs';
import {expect} from '@jest/globals';
import {DetailComponent} from "./detail.component";
import {SessionApiService} from "../../services/session-api.service";
import {TeacherService} from "../../../../services/teacher.service";
import {SessionService} from "../../../../services/session.service";
import spyOn = jest.spyOn;

describe('DetailComponent', () => {
  let component: DetailComponent;
  let fixture: ComponentFixture<DetailComponent>;

  const sessionDetails = {
    token: '',
    type: '',
    id: 1,
    username: '',
    firstName: '',
    lastName: '',
    admin: false
  };

  let sessionApiServiceMock: Partial<SessionApiService> = {
    delete: jest.fn().mockReturnValue(of({})),
    detail: jest.fn().mockReturnValue(of(sessionDetails)),
    participate: jest.fn().mockReturnValue(of({})),
    unParticipate: jest.fn().mockReturnValue(of({}))
  };

  const teacherService: Partial<TeacherService> = {
    detail: jest.fn().mockReturnValue(of())
  };

  const mockSessionService = {
    sessionInformation: sessionDetails
  };

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule,
        HttpClientModule,
        MatSnackBarModule,
        ReactiveFormsModule
      ],
      declarations: [DetailComponent],
      providers: [
        { provide: SessionService, useValue: mockSessionService },
        { provide: SessionApiService, useValue: sessionApiServiceMock },
        { provide: TeacherService, useValue: teacherService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(DetailComponent);
    component = fixture.componentInstance;
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should load session details on initialization', () => {
    // Given
    component.sessionId = '1';

    // When
    component.ngOnInit();

    // Then
    expect(sessionApiServiceMock.detail).toHaveBeenCalled();
    expect(sessionApiServiceMock.detail).toHaveBeenCalledWith('1');

    expect(component.session).toEqual(sessionDetails);
  });

  it('should navigate to previous page when back is clicked', () => {
    // Given
    const backSpy = spyOn(window.history, 'back');

    // When
    component.back();

    // Then
    expect(backSpy).toHaveBeenCalled();
  })

  it('should call delete function on delete', () => {
    // Given
    component.sessionId = '1';
    const sessionApiServiceDeleteSpy = spyOn(sessionApiServiceMock, 'delete');
    const matSnackBarOpenSpy = spyOn(TestBed.inject(MatSnackBar), 'open');

    // When
    component.delete();

    // Then
    expect(sessionApiServiceDeleteSpy).toHaveBeenCalled();
    expect(matSnackBarOpenSpy).toHaveBeenCalledWith('Session deleted !', 'Close', { duration: 3000 });
  });

  it('should call participate function on participate', () => {
    // Given
    const sessionApiServiceParticipateSpy = spyOn(sessionApiServiceMock, 'participate');

    // When
    component.participate();

    // Then
    expect(sessionApiServiceParticipateSpy).toHaveBeenCalled();
  });

  it('should call unParticipate function on unParticipate', () => {
    // Given
    const sessionApiServiceUnParticipateSpy = spyOn(sessionApiServiceMock, 'unParticipate');

    // When
    component.unParticipate();

    // Then
    expect(sessionApiServiceUnParticipateSpy).toHaveBeenCalled();
  });


});
