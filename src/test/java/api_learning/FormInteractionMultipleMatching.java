package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;
import java.util.NoSuchElementException;

public class FormInteractionMultipleMatching {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        driver.get("https://the-internet.herokuapp.com/login");

        try{
            By loginInputFieldsSel = By.tagName("input");

            List<WebElement> loginFormFieldsElem = driver.findElements(loginInputFieldsSel);
            final int USERNAME_INDEX = 0;
            final int PASSWORD_INDEX = 1;
            if (!loginFormFieldsElem.isEmpty()) {
                loginFormFieldsElem.get(USERNAME_INDEX).sendKeys("tomsmith");
                loginFormFieldsElem.get(PASSWORD_INDEX).sendKeys("SuperSecretPassword!");
            } else {
                System.out.println("Cannot find the element!");
            }

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
