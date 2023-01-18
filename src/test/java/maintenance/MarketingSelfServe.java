package maintenance;/*
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

    @BeforeMethod
    void goBackToHomePage() {
        open(MSS_URL);
    }

    @Test(enabled = true, groups = {"regression", "demo"}, priority = 1, description = "checkHeaderText")
    void checkHeaderText() {
        SelenideElement headerText = $(By.xpath("//a[contains(@class, 'header__site-link')]"));
        Assert.assertTrue(headerText.isDisplayed(), "header text issue");
        Assert.assertEquals(headerText.getText(), "Marketing Self-Serve".toUpperCase(), "header text mismatch");
    }

    @Test(enabled = true, groups = {"regression", "demo"}, priority = 2, description = "library check")
    void checkLibrarySize() throws InterruptedException {
        $(By.xpath("//ul[contains(@class, 'menu')]//a[contains(@href, 'categoryID=100048619')]")).shouldBe(Condition.visible).click();
        //TimeUnit.SECONDS.sleep(2);
        SelenideElement iFrame = $(By.id("acslibrary-frame"));
        switchTo().frame(iFrame);
        $(By.xpath("//div[contains(@class, 'filename')]")).shouldBe(Condition.visible);
        ElementsCollection libraryElements = $$(By.xpath("//div[contains(@class, 'filename')]"));
        Assert.assertTrue(libraryElements.size() > 0);
    }

    @Test(enabled = true, groups = {"regression", "demo"}, priority = 2, description = "library check")
    void checkNewlyAddedLibraryElements() throws InterruptedException {
        $(By.xpath("//ul[contains(@class, 'menu')]//a[contains(@href, 'categoryID=100048619')]")).shouldBe(Condition.visible).click();
        //TimeUnit.SECONDS.sleep(2);
        SelenideElement iFrame = $(By.id("acslibrary-frame"));
        switchTo().frame(iFrame);
        $(By.xpath("//a[contains(@class, 'Newly_Added')]")).shouldBe(Condition.visible).click();
        $(By.xpath("//td[contains(@class, 'newfile')]")).shouldBe(Condition.visible);
        ElementsCollection libraryNewAddedElements = $$(By.xpath("//td[contains(@class, 'newfile')]"));
        Assert.assertTrue(libraryNewAddedElements.size() > 0);
    }

    @Test(enabled = true, groups = {"regression", "demo"}, priority = 3, description = "Run Asset activity report")
    void checkAssetActivityReport() throws InterruptedException {
        $(By.xpath("//li[contains(@class, 'last expanded')]")).shouldBe(Condition.visible).click();
        SelenideElement iFrame = $(By.id("acslibrary-frame"));
        switchTo().frame(iFrame);
        $(By.xpath("//div[contains(@class, 'adminSectionOdd')]/div[contains(@class, 'adminLink')]")).shouldBe(Condition.visible).click();
        $(By.xpath("//a[contains(@href, 'usageReportByUser')]")).shouldBe(Condition.visible).click();
        $(By.xpath("//input[contains(@value, 'byMonth')]")).shouldBe(Condition.visible).click();
        $(By.id("submitButton")).shouldBe(Condition.visible).click();
        switchTo().window(1);
        SelenideElement assetsActivityReport = $(By.xpath("//table[2]/tbody")).shouldBe(Condition.visible);
        String childElementCount = assetsActivityReport.getAttribute("childElementCount");
        Assert.assertEquals(childElementCount, "6", "assetsActivityReport do not contain 6 rows");
    }
}
