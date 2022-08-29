package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

@ComponentCssSelector(value = "#opc-payment_method")
public class PaymentMethodComponent extends Component {

    private static final By codSel = By.cssSelector("[value = 'Payments.CashOnDelivery']");
    private static final By checkMoneyOrderSel = By.cssSelector("[value='Payments.CheckMoneyOrder']");
    private static final By creditCardSel = By.cssSelector("[value = 'Payments.Manual']");
    private static final By purchaseOrderSel = By.cssSelector("[value = 'Payments.PurchaseOrder']");
    private static final By continueBtnSel = By.cssSelector(".payment-method-next-step-button");

    public PaymentMethodComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectCODMethod() {
        component.findElement(codSel).click();
    }

    public void selectCheckMoneyOrderMethod() {
        component.findElement(checkMoneyOrderSel).click();
    }

    public void selectCreditCardMethod() {
        component.findElement(creditCardSel).click();
    }

    public void selectPurchaseOrderMethod() {
        component.findElement(purchaseOrderSel).click();
    }

    public void clickOnContinueBtn() {
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }
}
