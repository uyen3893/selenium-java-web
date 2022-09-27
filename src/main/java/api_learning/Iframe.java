package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import url.Urls;

public class Iframe implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            driver.get(baseUrl.concat(iframeSlug));

            By iframeSel = By.cssSelector("[id$='ifr']");
            WebElement iframeElem = driver.findElement(iframeSel);

            driver.switchTo().frame(iframeElem);

            WebElement inputFieldElem = driver.findElement(By.cssSelector("#tinymce"));
            inputFieldElem.click();
            inputFieldElem.clear();
            inputFieldElem.sendKeys("new text");

            driver.switchTo().defaultContent();
            driver.findElement(By.linkText("Elemental Selenium")).click();
            Thread.sleep(1000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
