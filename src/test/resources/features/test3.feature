Feature: Test3

  Scenario: Get and verify email users
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I verify duplicate emails