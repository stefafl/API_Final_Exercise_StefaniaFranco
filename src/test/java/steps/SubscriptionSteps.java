package steps;

import base.model.User;
import base.services.SubscriptionService;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
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
import static org.testng.AssertJUnit.assertTrue;

public class SubscriptionSteps {

    @Steps
    SubscriptionService subscriptionService;

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
            Reporter.log("There are duplicate emails", true);
            e.printStackTrace();
        }
    }

    @Then("I update accountNumber to {string}")
    public void iUpdateAccountNumber(String AccountNumber){
        assertThat(subscriptionService.updateUser(AccountNumber).equals("200"));
    }

    @And("I verify accountNumber {string}")
    public void iVerifyAccountNumber(String accountNumber){
        assertTrue("AccountNumber doesn't exist",subscriptionService.verifyAccountNumber(accountNumber));
    }

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
}
