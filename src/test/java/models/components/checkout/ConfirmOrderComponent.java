package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.testng.Assert;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ComponentCssSelector(value = "#opc-confirm_order")
public class ConfirmOrderComponent extends Component {

    private static final By priceTableRowSel = By.cssSelector(".cart-total tr");
    private static final By priceTypeSel = By.cssSelector(".cart-total-left");
    private static final By priceValueSel = By.cssSelector(".cart-total-right");
    private static final By continueBtnSel = By.cssSelector(".confirm-order-next-step-button");

    public ConfirmOrderComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public Map<String, Double> priceCategories() {
        Map<String, Double> priceCategories = new HashMap<>();
        scrollDownElement(component.findElement(priceTableRowSel));
        List<WebElement> priceTableRowsElem = component.findElements(priceTableRowSel);
        for (WebElement tableRowElem : priceTableRowsElem) {
            WebElement priceTypeElem = tableRowElem.findElement(priceTypeSel);
            WebElement priceValueElem = tableRowElem.findElement(priceValueSel);
            String priceType = priceTypeElem.getText().trim().replace(":", "");
            double priceValue = Double.parseDouble(priceValueElem.getText().trim());
            priceCategories.put(priceType, priceValue);
        }
        return priceCategories;
    }

    public void clickOnContinueBtn() {
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }


}
