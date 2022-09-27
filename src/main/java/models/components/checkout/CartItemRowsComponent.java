package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".cart-item-row")
public class CartItemRowsComponent extends ConfirmOrderComponent {

    private static final By unitPriceSel = By.cssSelector(".product-unit-price");
    private static final By quantitySel = By.cssSelector(".qty");
    private static final By productSubTotalSel = By.cssSelector(".product-subtotal");

    public CartItemRowsComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public double getUnitPrice() {
        return Double.parseDouble(component.findElement(unitPriceSel).getText().trim());
    }

    public int getQuantity() {
        return Integer.parseInt(component.findElement(quantitySel).getText().trim());
    }

    public double getProductSubTotal() {
        return Double.parseDouble(component.findElement(productSubTotalSel).getText().trim());
    }

}
