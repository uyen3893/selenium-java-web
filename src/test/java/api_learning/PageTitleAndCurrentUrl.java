package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class PageTitleAndCurrentUrl {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        driver.get("https://the-internet.herokuapp.com/login");

        try{
            driver.get("https://the-internet.herokuapp.com/login");
            System.out.println(driver.getTitle());
            System.out.println(driver.getCurrentUrl());

            WebElement loginBtnElem = driver.findElement(By.cssSelector("[type='submit']"));

            //get attribute
            System.out.println(loginBtnElem.getAttribute("type"));
            System.out.println(loginBtnElem.getCssValue("background-color"));

            //go back to previous page
            driver.navigate().back();

            //refresh page
            driver.navigate().refresh();

            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
