package api;/*
Created by Pavel Gryshchenko on 09.12.2022
*/

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

public class GetWebsiteResponseDemo {
        @Test
        public void GetUrlResponse()
        {
            // Specify the base URL to the RESTful web service
            RestAssured.baseURI = "https://fordcommercialvehiclecenter.com/";
            // Get the RequestSpecification of the request to be sent to the server
            RequestSpecification httpRequest = RestAssured.given();

            Response response = httpRequest.get("");
            //response.then().log().all();
            // Get the status code of the request.
            //If request is successful, status code will be 200
            int statusCode = response.getStatusCode();

            // Assert that correct status code is returned.
            Assert.assertEquals(statusCode, 200 ,"InCorrect status code returned");

        }
}
