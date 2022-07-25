package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ui.WaitMoreThanOneTab;
import url.Urls;

import java.time.Duration;

public class ExplicitWait implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(loginSlug));

            By notExistedSel = By.id("notExisted");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
//            wait.until(ExpectedConditions.invisibilityOfElementLocated(notExistedSel));
            wait.until(new WaitMoreThanOneTab());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
