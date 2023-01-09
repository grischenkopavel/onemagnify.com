package mainantanance;/*
Created by Pavel Gryshchenko on 19.12.2022
*/

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class MarketingSelfServe {
    private final String MSS_URL = "https://marketingselfserve.com/";

    @DataProvider(name = "environments")
    public Object[][] dataProviderMethod() {
        return new Object[][]{{"stage, https://marketingselfserve.com/"},
                {"prod , https://qa.marketingselfserve.com/"}};
    }

    @BeforeTest
    void setUp() {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 6000; // default 4000 ms
        open(MSS_URL);
        $(By.id("edit-name")).setValue("pgrischenko");
        $(By.id("edit-pass")).setValue("hXgStUXJ");
        $(By.id("edit-submit")).click();
    }

    @Test(groups = {"regression", "demo"}, priority = 0, description = "checkHeaderText")
    void checkHeaderText() {
        SelenideElement headerText = $(By.xpath("//a[contains(@class, 'header__site-link')]"));
        Assert.assertTrue(headerText.isDisplayed(), "header text issue");
        Assert.assertEquals(headerText.getText(), "Marketing Self-Serve".toUpperCase(), "header text mismatch");
    }

    @Test(groups = {"regression", "demo"}, priority = 1, description = "library check")
    void checkFirstLibraryElement() throws InterruptedException {
        $(By.xpath("//ul[contains(@class, 'menu')]//a[contains(@href, 'categoryID=100048619')]")).shouldBe(Condition.visible).click();
        TimeUnit.SECONDS.sleep(2);
        SelenideElement iFrame = $(By.id("acslibrary-frame"));
        switchTo().frame(iFrame);
        $(By.xpath("//div[contains(@class, 'filename')]")).shouldBe(Condition.visible);
        ElementsCollection libraryElements = $$(By.xpath("//div[contains(@class, 'filename')]"));
        Assert.assertTrue(libraryElements.size() > 0);
    }
}
