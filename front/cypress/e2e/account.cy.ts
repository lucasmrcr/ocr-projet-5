/// <reference types="cypress" />

describe('Account', () => {
  it('should login successfully', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {body: {id: 1}});

    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type('password{enter}{enter}');

    cy.url().should('include', '/sessions');
  });

  it('should retrieve user details', () => {
    cy.intercept('GET', '/api/user/1', {body: {id: 1}});

    cy.get('[routerlink="me"]').click();
    cy.url().should('include', '/me');
    cy.get('.my2 > .mat-focus-indicator').should('exist');
  });


  it('should user can delete his own account', () => {
    cy.intercept('GET', '/api/user/1', {body: {id: 1}});
    cy.intercept('DELETE', '/api/user/1', {statusCode: 200});

    cy.url().should('include', '/me');
    cy.get('.my2 > .mat-focus-indicator').click();
    cy.get('[routerlink="login"]').should('exist');
    cy.get('[routerlink="register"]').should('exist');
  });

});
