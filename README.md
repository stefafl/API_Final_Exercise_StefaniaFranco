# Final Exercise API Automaation

This project is developed as a Maven project. It use as an endpoint https://6294d1b0a7203b3ed071df5c.mockapi.io/api/bank_transaction/:endpoint

Consist of the following tests:

###@Test1
Verify the Endpoint is empty (If it has any data use the DELETE request to clean and leave it empty)

###@Test2
Initialize the POJO with 10 random data. Make a code verification for avoiding duplicate email accounts and then, perform a POST request.

###@Test3
Make a GET request, asserting that there are not duplicate email accounts.

###@Test4
Update an existing AccountNumber.

To run the project, open it in IntelliJ or similar IDEA. You can run it in the prompt using mvn verify or mvn test, or you can lso run it from the feature file.