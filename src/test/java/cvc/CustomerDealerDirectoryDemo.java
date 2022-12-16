package cvc;/*
Created by Pavel Gryshchenko on 06.12.2022
*/

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Condition.attribute;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.url;

public class CustomerDealerDirectoryDemo {
    private final int EXPECTED_TOTAL_DEALER_NUMBER = 662;
    private final String EXPECTED_SHOPPING_TOOLS_URL = "https://www.fordupfits.com/commercial/commercial-shopping-tools";
    @BeforeMethod
    void before() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000; // default 4000 ms
        open("https://fordcommercialvehiclecenter.com/");
    }
    @Test (enabled = true)
    void checkCVCLogo(){
        SelenideElement mainCvcLogo = $(By.xpath("//div[contains(@class, 'showForDesktop')]//img[contains(@class, 'center')]"));
        Assert.assertTrue(mainCvcLogo.isDisplayed(), "Logo doesn't appear");
    }
    @Test (enabled = true)
    void checkCvcLogoForSearch() throws InterruptedException {
        $(By.xpath("//img[contains(@id, 'CurrentLocationDesktop')]"))
                .shouldBe(visible)
                .click();

//        //SelenideElement showAllClick = $(By.id("showAllClickDesktop")).shouldBe(visible);
//        SelenideElement showAllClick = $(By.xpath("//li[contains(@id, 'showAllClickDesktop')]")).shouldBe(visible);
//        System.out.println(showAllClick.isDisplayed());
//        TimeUnit.SECONDS.sleep(5);
//        showAllClick.click();

        $(By.xpath("//img[contains(@class, 'cvc-logo')]")).shouldBe(visible);

        ElementsCollection logoElements = $$(By.xpath("//img[contains(@class, 'cvc-logo')]"));
        System.out.println("logoElements.size() " + logoElements.size());
        if (logoElements.size() == 10){
            System.out.println("Logo appeared");
        }else {
            Assert.fail("Logo doesn't appear");
        }
        TimeUnit.SECONDS.sleep(4);
    }
    @Test (enabled = true)
    void checkMdLogoForSearch() throws InterruptedException {
        $(By.xpath("//img[contains(@id, 'CurrentLocationDesktop')]"))
                .shouldBe(visible)
                .click();
        $(By.id("advanceFilterDesktop")).shouldBe(visible).click();
        $(By.id("CDD_SRVC_FS6")).click();
        $(By.id("applyFilterDesktop")).shouldBe(visible).click();

        $(By.xpath("//img[contains(@class, 'award')]")).shouldBe(visible);

        ElementsCollection logoElements = $$(By.xpath("//img[contains(@class, 'award')]"));
        System.out.println("logoElements.size() " + logoElements.size());
        if (logoElements.size() == 10){
            System.out.println("Logo appeared");
        }else {
            Assert.fail("Logo doesn't appear");
        }
        TimeUnit.SECONDS.sleep(4);
    }
    @Test(enabled = true)
    void getNumberOfActiveCvcDealersCustomerDD() throws InterruptedException, NumberFormatException {
        $(By.xpath("//img[contains(@id, 'CurrentLocationDesktop')]"))
                .shouldBe(visible)
                .click();

        SelenideElement totalDealerCount = $(By.xpath("//div[contains(@id, 'totalDealerCount')]"))
                .shouldBe(visible)
                .shouldHave(attribute("outerText"));
        String totalDealerCountText = totalDealerCount.getAttribute("outerText").trim();

        int totalDealerNumber = Integer.parseInt(totalDealerCountText.replaceAll("\\D", ""));

        Assert.assertEquals(totalDealerNumber, EXPECTED_TOTAL_DEALER_NUMBER, "mismatch of active cvc dealers");
        TimeUnit.SECONDS.sleep(2);
    }
    @Test
    void checkShoppingToolsLinkRedirection() throws InterruptedException {
        $(By.id("shoppingToolsMenu")).shouldBe(visible).click();
        TimeUnit.SECONDS.sleep(2);
        String currentUrl = url();
        System.out.println(currentUrl);
        Assert.assertEquals(currentUrl, EXPECTED_SHOPPING_TOOLS_URL, "ShoppingToolsLinkRedirection is wrong");
    }
    @Test
    void checkSignUp() throws InterruptedException {
        $(By.id("stayConnectedMenu")).shouldBe(visible).click();
        $(By.id("firstName")).shouldBe(visible).setValue("QA");
        $(By.id("lastName")).shouldBe(visible).setValue("Automation");
        $(By.id("emailAddress")).shouldBe(visible).setValue("Automation@email.com");
        $(By.id("zipcode")).shouldBe(visible).setValue("90210");
        $(By.id("keepMeUpdatedBtn")).shouldBe(visible).click();

        TimeUnit.SECONDS.sleep(2);

        SelenideElement userSuccessRegisterMessage = $(By.xpath("//div[contains(@class, 'reveal-msg-text')]/p[2]"));
        Assert.assertTrue(userSuccessRegisterMessage.isDisplayed(), "user SuccessRegister Message do not appear");

        Assert.assertTrue(userSuccessRegisterMessage.getText().contains("Automation@email.com"), "SuccessRegister Message do not contain email");
    }
    @Test(enabled = false)
    void checkDealerSearch() throws InterruptedException {
        $(By.id("searchDealerLocation")).shouldBe(visible).setValue("DON SANDERSON FORD").click();
        TimeUnit.SECONDS.sleep(2);
        //Assert.assertEquals(currentUrl, EXPECTED_SHOPPING_TOOLS_URL, "ShoppingToolsLinkRedirection is wrong");
    }
}
