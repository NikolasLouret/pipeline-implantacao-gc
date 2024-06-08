Feature: Patient Management API

  Scenario: Create a new patient
    When I create a patient with name "Nikolas", surname "Louret", gender "M", birthDate "1990-01-01", height 170, weight 70.5, and CPF "050.964.716-24"
    Then the response status should be 200
    And the response should contain the patient details

  Scenario: Get all patients
    When I request all patients
    Then the response status should be 200
    And the response should contain a list of patients

  Scenario: Get patient by CPF
    Given a patient exists with CPF "644.483.016-80"
    When I request the patient with CPF "644.483.016-80"
    Then the response status should be 200
    And the response should contain the patient details

  Scenario: Update an existing patient
    Given a patient exists with CPF "075.300.136-53"
    When I update the patient with CPF "075.300.136-53" to have name "Nikolau", surname "Vieira", gender "M", birthDate "1990-01-01", height 175 and weight 72.0
    Then the response status should be 200
    And the response should contain the patient details

  Scenario: Delete a patient
    Given a patient exists with CPF "530.709.446-98"
    When I delete the patient with CPF "530.709.446-98"
    Then the response status should be 204

  Scenario: Calculate IMC of a patient
    Given a patient exists with CPF "212.427.896-77" having height 175, weight 70 and gender "M"
    When I request the patient with CPF "212.427.896-77"
    Then the response status should be 200
    And the response should contain the IMC 22.86

  Scenario: Classify IMC of a patient
    Given a patient exists with CPF "371.475.836-47" having height 160, weight 80 and gender "M"
    When I request the patient with CPF "371.475.836-47"
    Then the response status should be 200
    And the response should contain the IMC classification "Obesidade I"
