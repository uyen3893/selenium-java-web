package pom_tests;

import driver.DriverFactory;
import models.components.LoginFormComponent;
import models.pages.LoginPageMod03;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class LoginTestMod03 implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(loginSlug));

            LoginPageMod03 loginPageMod03 = new LoginPageMod03(driver);
            LoginFormComponent loginFormComponent = loginPageMod03.loginFormComponent();
            loginFormComponent.inputUsername("name");
            loginFormComponent.inputPassword("password");
            loginFormComponent.clickOnLoginBtn();

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
