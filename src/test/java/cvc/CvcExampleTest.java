package cvc;/*
Created by Pavel Gryshchenko on 23.11.2022
*/

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.conditions.Visible;
import org.openqa.selenium.By;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Selenide.*;


public class CvcExampleTest {
    @BeforeTest
    void before() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000; // default 4000 ms
        open("https://cvcqa.dealerconnection.com/home/init");
    }

    @Test
    void compareNumberOfActiveCvcDealers() throws InterruptedException, NumberFormatException {
        $(By.id("userName")).setValue("p-grisc6");
        $(By.id("password")).setValue("R32022mPE6t#");
        $(By.id("btn-sign-in")).click();

        $(By.id("//ul[contains(@id, 'top-level-nav')]//a[contains(text(),'REPORTS')]")).shouldBe(visible);
        //$(By.xpath("//a[contains(@href,'/reports/init')]")).click();
        TimeUnit.SECONDS.sleep(3);
    }
}
