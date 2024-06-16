/// <reference types="cypress" />

describe('login user', () => {
  it('should login successfully', () => {
    cy.visit('/login');

    cy.intercept('POST', '/api/auth/login', {statusCode: 200});

    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);
    cy.url().should('include', '/sessions');
  });

  it('should logout successfully', () => {
    cy.url().should('include', '/sessions');
    cy.get('.mat-toolbar > .ng-star-inserted > :nth-child(3)').click();
    cy.url().should('not.contain', '/sessions');
  });


  it('should login button be disabled while email field is empty', () => {
    cy.visit('/login');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);
    cy.get('button[type=submit]').should('be.disabled');
  });


  it('should login button be disabled while password field is empty', () => {
    cy.visit('/login');
    cy.get('input[formControlName=email]').type(`example@example.com{enter}{enter}`);
    cy.get('button[type=submit]').should('be.disabled');
  });

  it('should not login with bad credentials', () => {
    cy.visit('/login');

    cy.get('input[formControlName=email]').type('example@example.com');
    cy.get('input[formControlName=password]').type(`password{enter}{enter}`);
    cy.get('.error').should('be.visible');
  });

});
