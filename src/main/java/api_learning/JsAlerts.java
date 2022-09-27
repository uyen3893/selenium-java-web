package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import url.Urls;

import java.time.Duration;

public class JsAlerts implements Urls {

    private final static By JS_ALERT_SEL = By.cssSelector("[onclick='jsAlert()']");
    private final static By JS_ALERT_CONFIRM_SEL = By.cssSelector("[onclick='jsConfirm()']");
    private final static By JS_ALERT_PROMPT_SEL = By.cssSelector("[onclick='jsPrompt()']");
    private final static By RESULT_SEL = By.cssSelector("[#result]");

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(jsalertSlug));

            handleAlert(driver, JS_ALERT_SEL, true);

            handleAlert(driver, JS_ALERT_CONFIRM_SEL, false);

            handleAlert(driver, JS_ALERT_PROMPT_SEL, "selenium java");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static Alert triggerAlert(WebDriver driver, By alertSel) {
        WebElement jsAlertBtnElem = driver.findElement(alertSel);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        jsAlertBtnElem.click();
        return wait.until(ExpectedConditions.alertIsPresent());
    }

    private static void handleAlert(WebDriver driver, By alertSel, boolean isAccepting) {
        Alert alert = triggerAlert(driver, alertSel);
        System.out.println("Alert Content: " + alert.getText());
        if (isAccepting) {
            alert.accept();
        } else {
            alert.dismiss();
        }
    }

    private static void handleAlert(WebDriver driver, By alertSel, String text) {
        Alert alert = triggerAlert(driver, alertSel);
        System.out.println("Alert Content: " + alert.getText());
        alert.sendKeys(text);
        alert.accept();
    }
}
