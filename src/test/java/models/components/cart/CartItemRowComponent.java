package models.components.cart;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@ComponentCssSelector(value = ".cart-item-row")
public class CartItemRowComponent extends Component {

    private final static By unitPriceSel = By.cssSelector(".product-unit-price");
    private final static By itemQuantitySel = By.cssSelector(".qty-input");
    private final static By subTotalSel = By.cssSelector(".product-subtotal");

    public CartItemRowComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public double getUnitPrice() {
        return Double.parseDouble(component.findElement(unitPriceSel).getText().trim());
    }

    public int getItemQuantity() {
        return Integer.parseInt(component.findElement(itemQuantitySel).getAttribute("value").trim());
    }

    public double getSubTotal() {
        return Double.parseDouble(component.findElement(subTotalSel).getText().trim());
    }
}
