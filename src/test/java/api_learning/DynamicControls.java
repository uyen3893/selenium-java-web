package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import support.ui.WaitForEnableElement;
import url.Urls;

import java.time.Duration;

public class DynamicControls implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(dynamicControlsSlug));

            By checkboxFormSel = By.id("checkbox-example");
            WebElement checkboxFormElem = driver.findElement(checkboxFormSel);
            WebElement checkBoxElem = checkboxFormElem.findElement(By.tagName("input"));
            if (!checkBoxElem.isSelected()) {
                checkBoxElem.click();
            }

            Thread.sleep(1000);

            By inputFormSel = By.id("input-example");
            WebElement inputFormElem = driver.findElement(inputFormSel);
            WebElement inputElem = inputFormElem.findElement(By.tagName("input"));
            WebElement inputBtnElem = inputFormElem.findElement(By.tagName("button"));
            if (!inputElem.isEnabled()) {
                inputBtnElem.click();
            }

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(new WaitForEnableElement(By.cssSelector("#input-example input")));
            inputElem.sendKeys("Lab 18");

            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
