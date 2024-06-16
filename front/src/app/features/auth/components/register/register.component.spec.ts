import {HttpClientModule} from '@angular/common/http';
import {ComponentFixture, TestBed} from '@angular/core/testing';
import {ReactiveFormsModule} from '@angular/forms';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatIconModule} from '@angular/material/icon';
import {MatInputModule} from '@angular/material/input';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {RouterTestingModule} from '@angular/router/testing';
import {expect} from '@jest/globals';
import {RegisterComponent} from "./register.component";
import {AuthService} from "../../services/auth.service";
import {Router} from "@angular/router";
import spyOn = jest.spyOn;
import {of, throwError} from "rxjs";

describe('LoginComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;

  const authServiceMock: Partial<AuthService> = {
    register: jest.fn()
  };

  const routerMock: Partial<Router> = {
    navigate: jest.fn()
  };

  beforeEach(async () => {
    // Reset all mocks so that we can verify the number of calls to the methods
    jest.resetAllMocks();

    await TestBed.configureTestingModule({
      declarations: [RegisterComponent],
      providers: [
        { provide: AuthService, useValue: authServiceMock },
        { provide: Router, useValue: routerMock }
      ],
      imports: [
        RouterTestingModule,
        BrowserAnimationsModule,
        HttpClientModule,
        MatCardModule,
        MatIconModule,
        MatFormFieldModule,
        MatInputModule,
        ReactiveFormsModule
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });

  it('should successfully register', () => {
    // Given
    spyOn(authServiceMock, 'register').mockReturnValue(of());
    const registerRequest = {
      email: 'example@example.com',
      password: 'password',
      firstName: 'John',
      lastName: 'Doe'
    };

    component.form.setValue(registerRequest);

    // When
    component.submit();

    // Then
    expect(authServiceMock.register).toHaveBeenCalledTimes(1);
    expect(authServiceMock.register).toHaveBeenCalledWith(registerRequest);
    expect(component.onError).toBe(false);
  });

  it('should set error on register failure', () => {
    // Given
    spyOn(authServiceMock, 'register').mockReturnValue(throwError(() => new Error('Fields empty')));
    const registerRequest = {
      email: 'example@example.com',
      password: '',
      firstName: 'John',
      lastName: 'Doe'
    };

    component.form.setValue(registerRequest);

    // When
    component.submit();

    // Then
    expect(authServiceMock.register).toHaveBeenCalledTimes(1);
    expect(authServiceMock.register).toHaveBeenCalledWith(registerRequest);

    expect(routerMock.navigate).not.toHaveBeenCalled();
    expect(component.onError).toBe(true);
  });

});
