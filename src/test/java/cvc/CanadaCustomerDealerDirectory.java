package cvc;/*
Created by Pavel Gryshchenko on 19.12.2022
*/

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideDriver;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;

public class CanadaCustomerDealerDirectory {
    private final String CANADA_CUSTOMER_DD_URL = "https://www.fordcommercialvehiclecentres.ca/";
    private final String EXPECTED_CANADA_TOTAL_DEALER_NUMBER = "124";

    @BeforeMethod
    void before() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000; // default 4000 ms
        open(CANADA_CUSTOMER_DD_URL);
    }

    @Test(enabled = true, groups = {"regression", "demo", "db regression"}, description = "Check if cvc logo display on Canada DD homepage")
    void checkCVCLogo() {
        SelenideElement mainCvcLogo = $(By.xpath("//img[contains(@class, 'desktop-header-logo')]"));
        Assert.assertTrue(mainCvcLogo.isDisplayed(), "Logo doesn't appear");
    }

    @Test(enabled = true, groups = {"regression", "db regression"})
    void checkStayConnected() throws InterruptedException {
        $(By.xpath("//li[contains(@class, 'directory-nav')]")).shouldBe(visible).click();
        $(By.id("contactName")).shouldBe(visible).setValue("QA");
        $(By.id("businessName")).shouldBe(visible).setValue("BusinessName");
        $(By.id("emailAddress")).shouldBe(visible).setValue("Automation@email.com");
        $(By.id("keepMeUpdatedBtn")).shouldBe(visible).click();

        TimeUnit.SECONDS.sleep(2);

        Assert.assertTrue(switchTo().alert().getText().contains("Automation@email.com"), "SuccessRegister Message do not contain email");
        Assert.assertTrue(switchTo().alert().getText().contains("Thank you for joining!"), "SuccessRegister Message do not contain email");
    }

    @Test(enabled = true, groups = {"regression", "db regression"})
    void checkDealerSearch() throws InterruptedException {
        $(By.xpath("//li[contains(@class, 'commercial-nav')]")).shouldBe(visible).click();
        $(By.xpath("//a[contains(@onclick, 'return popUpFunction()')]")).shouldBe(visible).click();
        SelenideElement totalDealerCount = $(By.id("totalDealerCount")).shouldBe(visible);

        Assert.assertEquals(totalDealerCount.getText().trim(), EXPECTED_CANADA_TOTAL_DEALER_NUMBER, "mismatch of active cvc dealers");
        TimeUnit.SECONDS.sleep(2);
    }
}
