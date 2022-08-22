package models.components.cart;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@ComponentCssSelector(".cart-footer .totals")
public class TotalComponent extends Component {

    private final static By priceTableRowSel = By.cssSelector("table tr");
    private final static By priceTypeSel = By.cssSelector(".cart-total-left");
    private final static By priceValueSel = By.cssSelector(".cart-total-right");

    public TotalComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public Map<String, Double> priceCategories() {
        Map<String, Double> priceCategories = new HashMap<>();
        List<WebElement> priceTableRowElem = component.findElements(priceTableRowSel);
        //Loop for each row
        for (WebElement tableRowElem : priceTableRowElem) {
            WebElement priceTypeElem = tableRowElem.findElement(priceTypeSel);
            WebElement priceValueElem = tableRowElem.findElement(priceValueSel);

            //Get type and value's price of each row
            String priceType = priceTypeElem.getText().trim().replace(":", "");
            double priceValue = Double.parseDouble(priceValueElem.getText().trim());

            priceCategories.put(priceType, priceValue);
        }
        return priceCategories;
    }

}
