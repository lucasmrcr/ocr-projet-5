/// <reference types="cypress" />

describe('register new user', () => {
  it('should have been registered successfully', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {statusCode: 200});

    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);
    cy.url().should('include', '/login');
  });


  it('show error div when returned by api', () => {
    cy.visit('/register');

    cy.intercept('POST', '/api/auth/register', {statusCode: 400});

    cy.get('input[formControlName=firstName]').type('John');
    cy.get('input[formControlName=lastName]').type('Doe');
    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);
    cy.get('.error').should('be.visible');
  });


  it('should disable submit button if email field is empty', () => {
    cy.visit('/register');

    cy.get('input[formControlName=email]').should('have.class', 'ng-invalid');
    cy.get('button[type=submit]').should('be.disabled');
  });


});
