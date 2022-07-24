package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;

import java.util.ArrayList;
import java.util.List;

public class PageTitleAndCurrentUrl {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

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
        driver.get("https://github.com");
        driver.findElement(By.xpath("//a[@href='/login']")).click();
        driver.findElement(By.cssSelector("#login_field")).sendKeys("uyenhtnguyen1993@qa.team");
        driver.findElement(By.cssSelector("#password")).sendKeys("SuperPassword@123");
        driver.findElement(By.xpath("//input[@type='submit']")).click();

        driver.quit();
    }

}
