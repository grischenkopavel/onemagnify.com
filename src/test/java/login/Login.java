package login;/*
Created by Pavel Gryshchenko on 06.02.2023
*/

import org.openqa.selenium.By;

import java.util.concurrent.TimeUnit;

import static com.codeborne.selenide.Selenide.*;

public class Login {

    final private static String ADFS_URL = "faust.idp.ford.com/";
    private static String adfsUserName = "p-grisc6";
    private static String adfsPass = "R32022mPE6t#";

    public static void login(String loginUrl) throws InterruptedException {
        if (loginUrl.contains(ADFS_URL)) {
            $(By.id("userName")).setValue(adfsUserName);
            $(By.id("password")).setValue(adfsPass);
            $(By.id("btn-sign-in")).click();
            TimeUnit.SECONDS.sleep(2);
        } else {
            System.out.println("Non ADFS page");
        }
    }
}
