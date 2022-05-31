Feature: Test2

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
