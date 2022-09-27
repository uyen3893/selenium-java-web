package pom_tests;

import driver.DriverFactory;
import models.pages.LoginPageMod01;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class LoginTestMod01 implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(loginSlug));

            LoginPageMod01 loginPage = new LoginPageMod01(driver);
            loginPage.username().sendKeys("name");
            loginPage.password().sendKeys("password");
            loginPage.loginBtn().click();

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
