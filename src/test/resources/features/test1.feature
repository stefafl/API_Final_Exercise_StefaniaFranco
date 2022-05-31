Feature: Test1

  Scenario: Verify endpoint is empty
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I make sure the endpoint is empty