package api;/*
Created by Pavel Gryshchenko on 09.12.2022
*/

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetWebsiteResponseDemo {
    @DataProvider(name = "urls")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"https://fordcommercialvehiclecenter.com/"},
                {"https://cvcqa2.dealerconnection.com/"}, {"https://cvc.dealerconnection.com/"}, {"https://mbstarrewards.com/"}};
    }

    @Test(enabled = true, groups = {"regression", "demo"}, dataProvider = "urls", description = "Check response code")
    public void GetUrlResponse(String url) {
        // Specify the base URL to the RESTful web service
        RestAssured.baseURI = url;
        // Get the RequestSpecification of the request to be sent to the server
        RequestSpecification httpRequest = RestAssured.given();

        Response response = httpRequest.get("");
        //response.then().log().all();
        // Get the status code of the request.
        //If request is successful, status code will be 200
        int statusCode = response.getStatusCode();

        // Assert that correct status code is returned.
        Assert.assertEquals(statusCode, 200, "InCorrect status code returned");

    }
}
