package api_learning;

import dev.failsafe.internal.util.Assert;
import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

public class ElementDisplaying {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        driver.get("https://the-internet.herokuapp.com/login");

        List<WebElement> elementList = driver.findElements(By.tagName("input-asdf"));

        if (!elementList.isEmpty()) {
            System.out.println("The elements are still displaying on the page");
        } else {
            System.out.println("Cannot find the elements");
        }

        driver.quit();
    }
}
