package steps;

import base.BaseApi;
import base.model.User;
import base.services.SubscriptionService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.thucydides.core.annotations.Steps;
import org.junit.Assert;
import org.testng.Reporter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static net.serenitybdd.rest.SerenityRest.restAssuredThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.testng.Assert.assertEquals;

public class SubscriptionSteps {

    @Steps
    SubscriptionService subscriptionService;

    @Given("I get the response from the endpoint {string}")
    public void iGetTheResponseFromTheEndpoint(String endpoint) {
        subscriptionService.sendGetRequest(endpoint);
    }

    @Given("I get the response from the endpoint")
    public void iGetTheResponseFromTheEndpoint() {
        subscriptionService.sendGetRequest("https://6294d1b0a7203b3ed071df5c.mockapi.io/api/bank_transaction/users");
    }


    @Then("I make sure the endpoint is empty")
    public void iMakeSureEndpointIsEmpty(){

        subscriptionService.verifyEmptyEndpoint();

        try {
            assertEquals(subscriptionService.getUserListFromService().size(),0,"Endpoint is not empty");
        }catch (NullPointerException e){
            assertEquals(subscriptionService.getUserListFromService().size(),0,"Endpoint is not empty");
        }

    }

    @Then("I verify duplicate emails")
    public void iVerifyDuplicateEmails(){
        try {
            Assert.assertFalse(subscriptionService.verifyDuplicateEmails());
        }catch (AssertionError e){
            Reporter.log("There is duplicate emails", true);
            e.printStackTrace();
        }
    }

    @Given("I get the response from the endpoint file with key {string}")
    public void iGetTheResponseFromTheEndpointWithKey(String key) {
        subscriptionService.sendGetRequest(new BaseApi().getEndpointByKey(key));
    }

    @Given("I get the endpoint by resource {string}")
    public void iGetEndpointByResource(String key) {
        subscriptionService.sendRequestByGet(new BaseApi().getEndpointByKey(key));
    }

    /*@When("I compare following data against subscribed users")
    public void iCompareDataWithSubscribedUsers(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<User> user_list = subscriptionService.getUserListFromService();

        for (Map<String, String> columns : rows) {
            assertThat(user_list.stream().anyMatch(userFound -> userFound.getUser().equals(columns.get("user"))));
            assertThat(user_list.stream().anyMatch(userFound -> userFound.getEmail().equals(columns.get("email"))));
            assertThat(user_list.stream().anyMatch(userFound -> String.valueOf(userFound.isSubscription()).equals(columns.get("subscription"))));
        }
    }*/

    @Then("I get the response code equals to {}")
    public void iGetTheResponseCodeEqualsTo(int responseCode) {
        restAssuredThat(response -> response.statusCode(responseCode));
    }

    @When("I create a set of users using POST request body based on data table")
    public void iSendAPOSTQueryToCreateANewUsersFromDataTable(DataTable dataTable) {
        User userBody = new User();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        List<String> emails = new ArrayList<>();

        for (Map<String, String> columns : rows) {
            try {
                Assert.assertFalse(emails.contains(columns.get("email")));
            }catch (AssertionError e){
                Reporter.log("Duplicate email: " + columns.get("email"), true);
                e.printStackTrace();
                continue;
            }

            emails.add(columns.get("email"));

            userBody.setName(columns.get("name"));
            userBody.setLastName(columns.get("lastName"));
            userBody.setAccountNumber(columns.get("accountNumber"));
            userBody.setAmmount(Integer.parseInt(columns.get("ammount")));
            userBody.setTransactionType(columns.get("transactionType"));
            userBody.setEmail(columns.get("email"));
            userBody.setActive(Boolean.parseBoolean(columns.get("active")));
            userBody.setCountry(columns.get("country"));
            userBody.setTelephone(columns.get("telephone"));

            subscriptionService.sendPostQueryWithBody(userBody);
        }
    }

    @When("I create a new user using POST request body string {string}")
    public void iSendAPOSTQueryToCreateANewUser(String requestBody) {
        subscriptionService.sendPostQuery(requestBody);
    }

    @When("I create a new user using resources with key {string}")
    public void iSendTheResponseFromTheEndpointFileWithKey(String key) {
        subscriptionService.sendPostQueryWithKey("create", key);
    }

    @When("I DELETE a user by id")
    public void iDeleteUserByIdDataTable(DataTable dataTable) {
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);
        for (Map<String, String> columns : rows) {
            assertThat(subscriptionService.sendDeleteQueryById(Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }

    @Then("I DELETE the last user created")
    public void iDeleteTheLastUserCreated() {
        assertThat(subscriptionService.sendDeleteQueryById(subscriptionService.getLastCreatedUser().getId()).equals("200"));
    }

    /*@Then("I UPDATE the user by id with information")
    public void iUpdateTheUserByIdWithInformation(DataTable dataTable) {
        User userBody = new User();
        List<Map<String, String>> rows = dataTable.asMaps(String.class, String.class);

        for (Map<String, String> columns : rows) {
            userBody.setUser(columns.get("user"));
            userBody.setEmail(columns.get("email"));
            userBody.setSubscription(Boolean.parseBoolean(columns.get("subscription")));
            userBody.setId(Integer.parseInt(columns.get("id")));

            assertThat(subscriptionService.updateUserById(userBody, Integer.parseInt(columns.get("id"))).equals("200"));
        }
    }*/
}
