Feature: API final exercise

  Scenario: Test1 - Verify endpoint is empty
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I make sure the endpoint is empty

  Scenario: Test2 - POST 10 users
    When I create a set of users using POST request body based on data table
    |name|lastName|accountNumber|ammount|transactionType|email|active|country|telephone|
    |Julia|Buitrago|4567        |5456   |uno            |jubu@exam.com|true|Colombia|1234|
    |Stefania|Franco|4667        |5856   |uno            |jubu@exam.com|true|Colombia|1634|
    |Tatiana|Ortega|1667        |6856   |dos            |tatiO@exam.com|false|Colombia|1534|
    |Willie|Watson|2668        |8856   |tres            |wwatson@exam.com|true|USA|1834|
    |Samuel|Colorado|4367        |2856   |uno            |sam12@exam.com|false|Colombia|1634|
    |Luisa|Gonzalez|5667        |3856   |uno            |LuGo@exam.com|false|Colombia|1534|
    |Andres|Gonzalez|6667        |5756   |uno            |gonz13@exam.com|true|Colombia|1834|
    |Pepito|Tres|7667        |5886   |dos            |pepiTres@exam.com|false|Peru|1934|
    |Lulu|Fernandez|8667        |9856   |dos            |lulu4@exam.com|true|Colombia|1664|
    |Ana|Henao|9667        |3856   |uno            |anita6@exam.com|false|Francia|1638|
    Then I get the response code equals to 201

  Scenario: Test3 - Get and verify email users
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I verify duplicate emails

  Scenario: Test4 - Update an existing AccountNumber
    Given I get the response from the endpoint
    When I get the response code equals to 200
    And I verify accountNumber "5667"
    Then I update accountNumber to "1366"

    # DELETE Request based on a key and using JSON resource
  Scenario: Delete the last user created
    Given I get the response from the endpoint
    When I get the response code equals to 200
    Then I DELETE the last user created
#
#  # Get request scenario using endpoint by parameter
#  Scenario: Testing an Endpoint - Get action using string parameter by resource
#    Given I get the endpoint by resource "my_endpoint"
#    Then I get the response code equals to 200
#
#  # Get request scenario using data table by parameter
#  Scenario Outline: Testing an Endpoint - Get action using example table
#    Given I get the response from the endpoint <Endpoint>
#    Then I get the response code equals to <Status>
#
#    Examples:
#      | Endpoint                                                  | Status |
#      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |
#      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |
#      | "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data" | 200    |
#
#
#  # Get request scenario using data table by parameter with key
#  Scenario Outline: Testing an Endpoint - Get action using example table with key
#    Given I get the response from the endpoint file with key <Key>
#    Then I get the response code equals to <Status>
#
#    Examples:
#      | Key            | Status |
#      | "my_endpoint"  | 200    |
#      | "my_endpoint2" | 200    |
#      | "my_endpoint3" | 200    |
#
#
#  # Get request scenario using data table comparing against service response body
#  Scenario: Testing an Endpoint - Get action using example table comparing values
#    Given I get the response from the endpoint "https://61ef2acfd593d20017dbb33b.mockapi.io/api/v1/Data"
#    When I get the response code equals to 200
#    Then I compare following data against subscribed users
#      | user  | email            | subscription |
#      | User1 | myUser1@test.com | true         |
#      | User2 | myUser2@test.com | false        |
#      | User3 | myUser3@test.com | true         |