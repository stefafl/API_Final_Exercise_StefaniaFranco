package base.services;

import base.model.User;
import io.restassured.response.Response;
import net.serenitybdd.rest.SerenityRest;
import net.thucydides.core.annotations.Step;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * This Class contains all methods/actions needed by functions related to Subscription Service
 */
public class SubscriptionService {

    /**
     * Constants definitions
     */
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String SUBSCRIPTION_CONTENT_TYPE = "application/json";

    /**
     * Local variables
     */
    private Response response;
    private User user;

    /**
     * This method is used to send a GET request based on an endpoint
     *
     * @param endpoint (String)
     */
    @Step("I get the endpoint {string}")
    public void sendGetRequest(String endpoint) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .get(endpoint);
    }

    /**
     * This method send a POST query based on a body as String
     *
     * @param body
     */
    @Step("I send a POST query using body")
    public void sendPostQueryWithBody(Object body) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(body)
                .post("https://6294d1b0a7203b3ed071df5c.mockapi.io/api/bank_transaction/users");
    }

    /**
     * This method DELETE a user by id returning the Response to compare the status code
     *
     * @param id
     * @return Response object to assert/compare response code
     */
    @Step("I send a DELETE query by id {int}")
    public Response sendDeleteQueryById(int id) {
        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .when()
                .delete("https://6294d1b0a7203b3ed071df5c.mockapi.io/api/bank_transaction/users/" + id);

        return response;
    }

    /**
     * This method returns the list of users from the main service with all contained elements
     *
     * @return List of users from class User
     */
    @Step("I get the list of users from service")
    public List<User> getUserListFromService() {
        return SerenityRest.lastResponse().jsonPath().getList(".", User.class);
    }


    /**
     * This method updates a user account number using body information
     *
     * @param accountNumber
     * @return Response Object - Response code
     */
    @Step("I UPDATE accountNumber using information")
    public Response updateUser(String accountNumber) {
        this.user.setAccountNumber(accountNumber);

        response = SerenityRest.given()
                .contentType(CONTENT_TYPE)
                .header(CONTENT_TYPE, SUBSCRIPTION_CONTENT_TYPE)
                .body(this.user)
                .put("https://6294d1b0a7203b3ed071df5c.mockapi.io/api/bank_transaction/users/" + user.getId());


        return response;
    }
    /**
     * This method is used to verify the endpoint is empty, if is not deletes all the users
     */
    @Step("I verify endpoint is empty")
    public void verifyEmptyEndpoint(){
        List<User> users = getUserListFromService();

        if(!users.isEmpty()){
            deleteAllUsers(users.size());
        }
    }

    /**
     * This method is used to verify if an account number exists
     *
     * @param accountNumber (String)
     */
    @Step("I verify accountNumber")
    public boolean verifyAccountNumber(String accountNumber){
        List<User> users = getUserListFromService();
        List<String> accountNumbers = new ArrayList<>();

        for(User user : users){
            accountNumbers.add(user.getAccountNumber());
            if(accountNumbers.contains(accountNumber)){
                this.user = user;
                return true;
            }
        }
        return false;
    }

    /**
     * This method is used to verify if there is duplicate emails accounts
     */
    @Step("I verify duplicate emails")
    public boolean verifyDuplicateEmails(){
        List<User> users = getUserListFromService();
        List<String> emails = new ArrayList<>();

        for(User user : users){
            if(emails.contains(user.getEmail())){
                return true;
            }
            emails.add(user.getEmail());
        }
        return false;
    }

    /**
     * This method is used to DELETE all the user of an endpoint
     *
     * @param totalUsers (String)
     */
    @Step("I DELETE {int} users")
    public void deleteAllUsers(int totalUsers){
        for(int i = 1; i<=totalUsers; i++){
            assertThat(sendDeleteQueryById(i).equals("200"));
        }
    }

}
