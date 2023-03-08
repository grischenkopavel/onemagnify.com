package cvc;/*
Created by Pavel Gryshchenko on 20.12.2022
*/

import com.codeborne.selenide.Configuration;
import login.Login;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static com.codeborne.selenide.WebDriverRunner.url;

public class CbmFieldPortal {
    private final String CBM_FIELD_PORTAL_URL = "https://cvc.dealerconnection.com/fieldportal";

    @BeforeTest
    void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000; // default 4000 ms
        open(CBM_FIELD_PORTAL_URL);
    }

    @Test(enabled = true, groups = {"regression", "demo"}, description = "Get active cvc dealer count from CBM field portal")
    void getNumberOfActiveCvcDealers() throws InterruptedException, NumberFormatException {
        Login.login(url());

        String numberOfActiveCvcDealers = $(By.xpath("//span[contains(@id, 'totalDlrId')]")).shouldBe(visible).text();
        Assert.assertFalse(numberOfActiveCvcDealers.isEmpty(), "Number Of Active Cvc Dealers is null");
        System.out.println("active cvc dealer count from CBM field portal = " + numberOfActiveCvcDealers);
        TimeUnit.SECONDS.sleep(3);
    }
}
