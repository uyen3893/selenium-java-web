package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import test_data.PaymentMethod;

@ComponentCssSelector(value = ".billing-info")
public class BillingInfoComponent extends ConfirmOrderComponent {

    private static final By nameSel = By.cssSelector(".name");
    private static final By emailSel = By.cssSelector(".email");
    private static final By phoneSel = By.cssSelector(".phone");
    private static final By address1Sel = By.cssSelector(".address1");
    private static final By address2Sel = By.cssSelector(".address2");
    private static final By cityStateZipSel = By.cssSelector(".city-state-zip");
    private static final By countrySel = By.cssSelector(".country");
    private static final By paymentMethodSel = By.cssSelector(".payment-method");

    public BillingInfoComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void verifyNameBillingInfo(String firstname, String lastname) {
        Assert.assertEquals(component.findElement(nameSel).getText().trim(), firstname + " " + lastname,
                "[ERR] Name is incorrect");
    }
    public void verifyEmailBillingInfo(String email) {
        Assert.assertEquals(component.findElement(emailSel).getText().trim().replace("Email: ", ""),
                email, "[ERR] Email is incorrect!");
    }
    public void verifyPhoneBillingInfo(String phone) {
        Assert.assertEquals(component.findElement(phoneSel).getText().trim().replace("Phone: ", ""),
                phone, "[ERR] Phone is incorrect!");
    }
    public void verifyAddressBillingInfo(String address) {
        Assert.assertEquals(component.findElement(address1Sel).getText().trim(), address, "[ERR] Address is incorrect!");
    }
    public void verifyCityAndStateBillingInfo(String city, String state, String zipcode) {
        String actualResult = component.findElement(cityStateZipSel).getText().trim();
        String expectedResult = city + " , " + state + " " + zipcode;
        Assert.assertEquals(actualResult, expectedResult, "[ERR] City or State or Zip code is incorrect!");
    }
    public void verifyCountryBillingInfo(String country) {
        Assert.assertEquals(component.findElement(countrySel).getText().trim(), country, "[ERR] Country is incorrect!");
    }

    public void verifyPaymentMethod(PaymentMethod paymentMethod) {
        String actualResult = component.findElement(paymentMethodSel).getText().trim();
        String expectedResult = null;
        switch (paymentMethod) {
            case COD:
                expectedResult = "Cash On Delivery (COD)";
                break;
            case CHECK_MONEY_ORDER:
                expectedResult = "Check / Money Order";
                break;
            case PURCHASE_ORDER:
                expectedResult = "Purchase Order";
                break;
            case CREDIT_CARD:
                expectedResult = "Credit Card";
                break;
        }
        Assert.assertEquals(actualResult, expectedResult, "[ERR] Payment method is incorrect!");
    }
}
