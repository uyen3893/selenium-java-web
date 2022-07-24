package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import url.Urls;

import javax.swing.*;
import java.util.List;

public class MouseHover implements Urls {

    private final static By FIGURE_SEL = By.className("figure");
    private final static By PROFILE_NAME_SEL = By.cssSelector(".figcaption h5");
    private final static By PROFILE_LINK_SEL = By.cssSelector(".figcaption a");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(hoverSlug));

            List<WebElement> figureElems = driver.findElements(FIGURE_SEL);
            if(figureElems.isEmpty()) {
                throw new RuntimeException("There is no profile image displayed!");
            }

            Actions actions = new Actions(driver);
            for (WebElement figureElem : figureElems) {
                WebElement profileNameElem = driver.findElement(PROFILE_NAME_SEL);
                WebElement profileLinkElem = driver.findElement(PROFILE_LINK_SEL);

                System.out.println(profileLinkElem.getText() + ": " + profileLinkElem.isDisplayed());
                System.out.println(profileNameElem.getText() + ": " + profileNameElem.isDisplayed());

                actions.moveToElement(figureElem).perform();

                System.out.println(profileLinkElem.getText() + ": " + profileLinkElem.isDisplayed());
                System.out.println(profileNameElem.getText() + ": " + profileNameElem.isDisplayed());

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
