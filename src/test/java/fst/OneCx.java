package fst;/*
Created by Pavel Gryshchenko on 06.02.2023
*/

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import com.codeborne.selenide.WebDriverRunner;
import login.Login;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.*;

public class OneCx {
    final private String STAGE_URL = "https://onecxglobalqa.onemagnify.com/";
    final private String PROD_URL = "https://onecxglobal.com/";

    private SelenideElement topLogo = $(By.xpath("//img[contains(@src, 'oneCX-logo-RGB_Rv.svg')]"));
    private SelenideElement footerFordCopyright = $(By.id("copy-year"));
    private SelenideElement menuAssetLibrary = $(By.id("assetLibLink"));
    private SelenideElement menuResources = $(By.id("resourcesLink"));
    private SelenideElement menuEmailCampaigns = $(By.id("nav-dropdown"));
    private SelenideElement footerLogo = $(By.xpath("//footer[contains(@class, 'footer')]//div/div/img"));
    private SelenideElement footerFordLink = $(By.xpath("//footer[contains(@class, 'footer')]//a[contains(@href, 'ford')]"));
    private SelenideElement footerLincolnLink = $(By.xpath("//footer[contains(@class, 'footer')]//a[contains(@href, 'lincoln')]"));
    private ElementsCollection resourcesSet = $$(By.xpath("//div[contains(@class, 'fmc-cards__card')]"));

    private Date date = new Date();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY");
    private String currentYear = simpleDateFormat.format(date);

    @BeforeTest(groups = {"regression", "demo"}, description = "method in TestNG runs before the execution of all the test methods that are inside that folder.")
    void setUp() throws InterruptedException {
        Configuration.browserSize = "1920x1080";
        Configuration.timeout = 5000; // default 4000 ms
        open(STAGE_URL);
        Login.login(url());
    }

//    @BeforeMethod(description = "method in TestNG will execute before each test method")
//    void beforeMethod() {
//        open(STAGE_URL);
//    }

    @Test(description = "is OneCx logo visible?", priority = 1)
    void checkOneCxLogo() {
        Assert.assertTrue(topLogo.isDisplayed());
    }

    @Test(description = "Footer ford copyright and year", priority = 1)
    void checkFooterFordCopyright() {
        Assert.assertTrue(footerFordCopyright.isDisplayed());
        Assert.assertEquals(footerFordCopyright.getText().trim(), currentYear);
        Assert.assertTrue(footerFordCopyright.parent().getText().contains("Ford Motor Company"));
        Assert.assertTrue(footerFordCopyright.parent().getText().contains("©"));
    }

    @Test(description = "Footer ford logo", priority = 1)
    void checkFooterFordLogo() {
        Assert.assertTrue(footerLogo.isDisplayed());
        Assert.assertTrue(footerLogo.getAttribute("src").contains("ford-oval-lincoln-logo-RGB_Rv.svg"));
    }

    @Test(description = "Footer ford link redirection", priority = 2)
    void checkFooterFordLink() {
        Assert.assertTrue(footerFordLink.isDisplayed());

        //Scroll down till the bottom of the page
        executeJavaScript("window.scrollBy(0,document.body.scrollHeight)");
        footerFordLink.click();
        switchTo().window(1);
        Assert.assertTrue(title().contains("Ford"));

        WebDriverRunner.closeWindow();
        switchTo().window(0);
    }

    @Test(description = "Footer lincoln link redirection", priority = 2)
    void checkFooterLincolnLink() {
        Assert.assertTrue(footerLincolnLink.isDisplayed());

        //Scroll down till the bottom of the page
        executeJavaScript("window.scrollBy(0,document.body.scrollHeight)");
        footerLincolnLink.click();
        switchTo().window(1);
        Assert.assertTrue(title().contains("Lincoln"));

        WebDriverRunner.closeWindow();
        switchTo().window(0);
    }

    @Test(description = "AssetLibrary menu", priority = 2)
    void checkAssetLibraryMenu() throws InterruptedException {
        Assert.assertTrue(menuAssetLibrary.isDisplayed());
        TimeUnit.SECONDS.sleep(5);
    }

    @Test(description = "Resources menu", priority = 2)
    void checkResourcesMenu() throws InterruptedException {
        Assert.assertTrue(menuResources.isDisplayed());

        menuResources.click();
        Assert.assertFalse(resourcesSet.isEmpty(), "Resources menu do not contain training materials");
        TimeUnit.SECONDS.sleep(5);
    }

    @Test(description = "EmailCampaigns menu", priority = 2)
    void checkEmailCampaignsMenu() throws InterruptedException {
        Assert.assertTrue(menuEmailCampaigns.isDisplayed());

        menuEmailCampaigns.click();
        TimeUnit.SECONDS.sleep(5);
    }
}
