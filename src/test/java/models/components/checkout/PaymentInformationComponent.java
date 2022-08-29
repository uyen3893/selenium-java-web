package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import test_data.CreditCardType;

@ComponentCssSelector(value = "#opc-payment_info")
public class PaymentInformationComponent extends Component {

    private static final By creditCardDropdownSel = By.cssSelector("#CreditCardType");
    private static final By cardHolderNameSel = By.cssSelector("#CardholderName");
    private static final By cardNumberSel = By.cssSelector("#CardNumber");
    private static final By expiredMonthSel = By.cssSelector("#ExpireMonth");
    private static final By expiredYearSel = By.cssSelector("#ExpireYear");
    private static final By cardCodeSel = By.cssSelector("#CardCode");
    private static final By purchaseOrderNumberSel = By.cssSelector("#PurchaseOrderNumber");
    private static final By continueBtnSel = By.cssSelector(".payment-info-next-step-button");
//    private static final By textCODSel = By.cssSelector(".section payment-info");
    private static final By contentSel = By.cssSelector("#checkout-payment-info-load p");

    public PaymentInformationComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectCardType(CreditCardType creditCardType) {
        if (creditCardType == null) {
            throw new IllegalArgumentException("[ERR] Credit Card Type is not null!");
        }
        Select select = new Select(component.findElement(creditCardDropdownSel));
        switch(creditCardType) {
            case VISA:
                select.selectByVisibleText("Visa");
                break;
            case MASTER_CARD:
                select.selectByVisibleText("Master Card");
                break;
            case DISCOVER:
                select.selectByVisibleText("Discover");
                break;
            case AMEX:
                select.selectByVisibleText("Amex");
                break;
        }
    }

    public void inputCardHolderName(String name) {
        component.findElement(cardHolderNameSel).sendKeys(name);
    }

    public void inputCardNumber(String number) {
        component.findElement(cardNumberSel).sendKeys(number);
    }

    public void selectExpiredMonth(String month) {
        Select select = new Select(component.findElement(expiredMonthSel));
        select.selectByValue(month);
    }

    public void selectExpiredYear(String year) {
        Select select = new Select(component.findElement(expiredYearSel));
        select.selectByValue(year);
    }

    public void inputCardCode(String code) {
        component.findElement(cardCodeSel).sendKeys(code);
    }

    public void inputPurchaseOrderNumber(String number) {
        component.findElement(purchaseOrderNumberSel).sendKeys(number);
    }

    public void clickOnContinueBtn() {
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }

    public void verifySelectCODOption() {
        String contentCOD = component.findElement(contentSel).getText();
        Assert.assertEquals(contentCOD, "You will pay by COD", "[ERR] The payment method is not COD!");
    }

    public void verifySelectCheckMoneyOrderOption() {
        String contentCheckOrderMoney = component.findElement(contentSel).getText();
        Assert.assertEquals(contentCheckOrderMoney,
                "Mail Personal or Business Check, Cashier's Check or money order to:",
                "[ERR] The payment method is not check order money!");
    }
}
