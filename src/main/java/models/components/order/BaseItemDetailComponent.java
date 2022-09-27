package models.components.order;

import models.components.Component;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BaseItemDetailComponent extends Component {

    private final static By barNotificationSel = By.cssSelector("#bar-notification");
    private final static By productPriceSel = By.cssSelector(".product-price");
    private final static By addToCartBtnSel = By.cssSelector(".add-to-cart-button");

    public BaseItemDetailComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public double productPrice() {
        String price = component.findElement(productPriceSel).getText().trim();
        return Double.parseDouble(price);
    }

    public void clickAddToCart() {
        component.findElement(addToCartBtnSel).click();
    }

    public void waitUntilItemAddedToCart() {
        String successMsg = "The product has been added to your shopping cart";
        wait.until(ExpectedConditions.textToBePresentInElementLocated(barNotificationSel, successMsg));
    }
}
