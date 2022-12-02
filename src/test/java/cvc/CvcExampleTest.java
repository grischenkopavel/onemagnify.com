package cvc;/*
Created by Pavel Gryshchenko on 23.11.2022
*/

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.SelenideElement;
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
        open("https://fordcommercialvehiclecenter.com/");
    }

    @Test
    void compareNumberOfActiveCvcDealers() throws InterruptedException, NumberFormatException {
        $(By.xpath("//img[contains(@id, 'CurrentLocationDesktop')]"))
                .shouldBe(visible)
                .shouldHave(attribute("tagName"))
                .click();

//        SelenideElement totalDealerCountById = $(By.id("totalDealerCount"))
//                .shouldHave(attribute("outerText"));
//        System.out.println(totalDealerCountById.getText());

        SelenideElement totalDealerCount = $(By.xpath("//div[contains(@id, 'totalDealerCount')]"))
                .shouldBe(visible)
                .shouldHave(attribute("outerText"));
        String totalDealerCountText = totalDealerCount.getAttribute("outerText").trim();
        System.out.println("totalDealerCountText = " + totalDealerCountText);

        int totalDealerNumber = Integer.parseInt(totalDealerCountText.replaceAll("\\D", ""));
        System.out.println("\n Int totalDealerNumber = " + totalDealerNumber);
        System.out.println(totalDealerNumber);
        TimeUnit.SECONDS.sleep(3);
    }
}
