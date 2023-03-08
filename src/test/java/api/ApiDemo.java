package api;/*
Created by Pavel Gryshchenko on 09.12.2022
*/

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class ApiDemo {
    private final static String URL = "https://stage-ms-cvc-dashboard.preprod.apps.pcfonemagnify.com/cvc/dashboard/getallminimumservicehour/dealerpacode/20301/dealernum/71171";

    @Test
    public void getMinimumServiceHour() {
        Response response = given()
                .when()
                .get(URL)
                .then().log().all()//log to console
                .body("minimumServiceHour.salesCode", notNullValue())
                .body("minimumServiceHour.paCode", notNullValue())
                .body("minimumServiceHour.totalWeeklyServiceHours", notNullValue())
                .body("minimumServiceHour.requirementStatus", notNullValue())
                .extract().response();

        JsonPath jsonPath = response.jsonPath();
        String totalWeeklyServiceHours = jsonPath.get("minimumServiceHour.totalWeeklyServiceHours");
        String requirementStatus = jsonPath.get("minimumServiceHour.requirementStatus");
        System.out.println("Total Weekly Service Hours is " + totalWeeklyServiceHours + "\nMinimum Service Hours requirement status: " + requirementStatus);
    }
}
