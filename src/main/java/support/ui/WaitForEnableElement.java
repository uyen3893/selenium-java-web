package support.ui;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;

public class WaitForEnableElement implements ExpectedCondition<Boolean> {

    private By selector;

    public WaitForEnableElement(By selector) {
        this.selector = selector;
    }
    @Override
    public Boolean apply(WebDriver driver) {
        return driver.findElement(selector).isEnabled();
    }
}
