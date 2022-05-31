Feature: Test4

  Scenario: Test4 - Update an existing AccountNumber
    Given I get the response from the endpoint
    When I get the response code equals to 200
    And I verify accountNumber "5667"
    Then I update accountNumber to "1366"