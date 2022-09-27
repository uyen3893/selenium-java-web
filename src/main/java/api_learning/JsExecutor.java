package api_learning;

import driver.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import url.Urls;

public class JsExecutor implements Urls {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(baseUrl.concat(floatingMenuSlug));

            JavascriptExecutor javascriptExecutor = (JavascriptExecutor) driver;

            javascriptExecutor.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(2000);

            javascriptExecutor.executeScript("window.scrollTo(document.body.scrollHeight, 0);");
            Thread.sleep(2000);

        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
}
