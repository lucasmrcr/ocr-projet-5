import {ComponentFixture, TestBed} from '@angular/core/testing';
import {MatSnackBar} from '@angular/material/snack-bar';
import {Router} from '@angular/router';
import {of} from 'rxjs';
import {expect} from '@jest/globals';
import {MeComponent} from "./me.component";
import {SessionService} from "../../services/session.service";
import {UserService} from "../../services/user.service";

describe('MeComponent', () => {
  let component: MeComponent;
  let fixture: ComponentFixture<MeComponent>;
  let matSnackBarMock: Partial<MatSnackBar>;
  let routerMock: Partial<Router>;
  let sessionServiceMock: Partial<SessionService>;
  let userServiceMock: Partial<UserService>;

  beforeEach(() => {
    routerMock = {
      navigate: jest.fn()
    };
    sessionServiceMock = {
      sessionInformation: {
        token: '',
        type: '',
        id: 1,
        username: '',
        firstName: '',
        lastName: '',
        admin: false
      },
      logOut: jest.fn()
    };
    matSnackBarMock = {
      open: jest.fn()
    };
    userServiceMock = {
      getById: jest.fn().mockReturnValue(of({id: 1, firstName: 'John', lastName: 'Doe'})),
      delete: jest.fn().mockReturnValue(of({}))
    };

    TestBed.configureTestingModule({
      declarations: [MeComponent],
      providers: [
        {provide: Router, useValue: routerMock},
        {provide: SessionService, useValue: sessionServiceMock},
        {provide: MatSnackBar, useValue: matSnackBarMock},
        {provide: UserService, useValue: userServiceMock}
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(MeComponent);
    component = fixture.componentInstance;
  });

  it('should load user details on initialization', () => {
    // Given

    // When
    component.ngOnInit();

    // Then
    // getById should have been called with the session id
    expect(userServiceMock.getById).toHaveBeenCalledWith('1');
    // User should have been set to user defined in session service mock
    expect(component.user).toEqual({id: 1, firstName: 'John', lastName: 'Doe'});
  })

  it('should navigate to previous page when back is clicked', () => {
    // Given
    const backSpy = jest.spyOn(window.history, 'back');

    // When
    component.back();

    // Then
    expect(backSpy).toHaveBeenCalled();
  })

  it('should delete user account when delete is clicked', () => {
    // Given

    // When
    component.delete();

    // Then
    // delete should have been called with the session id
    expect(userServiceMock.delete).toHaveBeenCalledWith('1');
    // A snackbar should have been opened
    expect(matSnackBarMock.open).toHaveBeenCalledWith("Your account has been deleted !", 'Close', { duration: 3000 });
    // Session should have been logged out
    expect(sessionServiceMock.logOut).toHaveBeenCalled();
    // Should navigate to home page
    expect(routerMock.navigate).toHaveBeenCalledWith(['/']);
  })

});
