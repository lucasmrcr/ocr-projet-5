/// <reference types="cypress" />

describe('session operations', () => {
  it('should process as an admin login', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {body: {id: 1, admin: true}});

    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);

    cy.url().should('include', '/sessions');
  });

  it('should admin can create a new session', () => {
    cy.url().should('include', '/sessions');

    cy.intercept('GET', '/api/teacher', {body: [{id: 2, firstName: 'John', lastName: 'Doe'}]});
    cy.intercept('POST', '/api/session', {body: {id: 1}});
    cy.intercept('GET', '/api/session', {body: {id: 1}});

    cy.url().should('include', '/sessions');
    cy.get('button[routerLink=create]').click();
    cy.get('input[formControlName=name]').type('Session 1');
    cy.get('input[formControlName=date]').type('2024-06-17');
    cy.get('mat-select[formControlName=teacher_id]').click().get('mat-option').contains('John Doe').click();
    cy.get('textarea[formControlName=description]').type('Description 1');
    cy.get('button[type=submit]').click();
    cy.contains('Session created !').should('be.visible');
    cy.url().should('include', '/sessions');
  });

  it('should admin can update an existing session', () => {
    cy.visit('/login');
    cy.intercept('POST', '/api/auth/login', {id: 1, admin: true});

    cy.intercept('GET', '/api/session', [{id: 1}]);
    cy.intercept('GET', '/api/teacher', [{id: 2, firstName: 'John', lastName: 'Doe'}]);

    cy.get('input[formControlName="email"]').type('example@example.com');
    cy.get('input[formControlName="password"]').type('password{enter}{enter}');

    cy.intercept('GET', '/api/session/1', {
      id: 1,
      name: 'Session 1',
      description: 'Description 1',
      date: '2024-06-17',
      teacher_id: 1,
    });

    cy.contains('Edit').click();

    cy.intercept('PUT', '/api/session/1', {});
    cy.get('button[type="submit"]').click();

    cy.contains('Session updated !').should('be.visible');
  });

  it('should admin can delete a session', () => {
    cy.intercept('GET', '/api/session/1', {body: {id: 1}});
    cy.intercept('DELETE', '/api/session/1', {statusCode: 200});

    cy.url().should('include', '/sessions');
    cy.contains('Detail').click();
    cy.url().should('include', '/sessions/detail');
    cy.contains('Delete').click();
    cy.url().should('include', '/sessions');
    cy.contains('Session deleted !').should('be.visible');
  });

});

describe('participate', () => {
  it('should login as user', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {body: {id: 1, admin: false}});
    cy.intercept('GET', '/api/session', {body: [{id: 1}]});


    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type('password{enter}{enter}');

    cy.url().should('include', '/sessions');
  });

  it('should user can participate', () => {
    cy.intercept('GET', '/api/session/1', {body: {id: 1, users: []}});

    cy.url().should('include', '/sessions');
    cy.contains('Detail').click();
    cy.url().should('include', '/sessions/detail');
    cy.contains('Participate').should('be.visible');
  });
});

describe('do not participate', () => {
  it('should login as user', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {body: {id: 1, admin: false}});
    cy.intercept('GET', '/api/session', {body: [{id: 1}]});


    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type('password{enter}{enter}');

    cy.url().should('include', '/sessions');
  });

  it('should user can not participate', () => {
    cy.intercept('GET', '/api/session/1', {body: {id: 1, users: [ 1]}});

    cy.url().should('include', '/sessions');
    cy.contains('Detail').click();
    cy.url().should('include', '/sessions/detail');
    cy.contains('Do not participate').should('be.visible');
  });
});
