package pom_tests;

import driver.DriverFactory;
import models.pages.LoginPageMod02;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class LoginTestMod02 implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(loginSlug));
            LoginPageMod02 loginPageMod02 = new LoginPageMod02(driver);
            loginPageMod02.inputUsername("name");
            loginPageMod02.inputPassword("password");
            loginPageMod02.clickOnLoginBtn();

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
