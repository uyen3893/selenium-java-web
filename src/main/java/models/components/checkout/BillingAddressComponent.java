package models.components.checkout;

import models.components.Component;
import models.components.ComponentCssSelector;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

@ComponentCssSelector(value = "#opc-billing")
public class BillingAddressComponent extends Component {

    private static final By billingAddressDropdownSel = By.cssSelector("#billing-address-select");
    private static final By firstNameSel = By.cssSelector("#BillingNewAddress_FirstName");
    private static final By lastNameSel = By.cssSelector("#BillingNewAddress_LastName");
    private static final By emailSel = By.cssSelector("#BillingNewAddress_Email");
    private static final By countryDropdownSel = By.cssSelector("#BillingNewAddress_CountryId");
    private static final By stateProvinceDropdownSel = By.cssSelector("#BillingNewAddress_StateProvinceId");
    private static final By loadingStateProgressBarSel = By.cssSelector("#states-loading-progress");
    private static final By citySel = By.cssSelector("#BillingNewAddress_City");
    private static final By address1Sel = By.cssSelector("#BillingNewAddress_Address1");
    private static final By zipCodeSel = By.cssSelector("#BillingNewAddress_ZipPostalCode");
    private static final By phoneNumberSel = By.cssSelector("#BillingNewAddress_PhoneNumber");
    private static final By continueBtnSel = By.cssSelector(".new-address-next-step-button");

    public BillingAddressComponent(WebDriver driver, WebElement component) {
        super(driver, component);
    }

    public void selectBillingAddress() {
        if (!component.findElements(billingAddressDropdownSel).isEmpty()) {
            Select select = new Select(component.findElement(billingAddressDropdownSel));
            select.selectByVisibleText("New Address");
        }
    }

    public void inputFirstName (String firstname) {
        component.findElement(firstNameSel).sendKeys(firstname);
    }

    public void inputlastName (String lastname) {
        component.findElement(lastNameSel).sendKeys(lastname);
    }

    public void inputEmail(String email) {
        component.findElement(emailSel).sendKeys(email);
    }

    public void selectCountry(String country) {
        Select select = new Select(component.findElement(countryDropdownSel));
        select.selectByVisibleText(country);
        wait.until(ExpectedConditions.invisibilityOf(component.findElement(loadingStateProgressBarSel)));
    }

    public void selectState (String state) {
        Select select = new Select(component.findElement(stateProvinceDropdownSel));
        select.selectByVisibleText(state);
        wait.until(ExpectedConditions.invisibilityOf(component.findElement(loadingStateProgressBarSel)));
    }

    public void inputCity(String city) {
        component.findElement(citySel).sendKeys(city);
    }

    public void inputAddress1(String address) {
        component.findElement(address1Sel).sendKeys(address);
    }

    public void inputZipCode(String zipcode) {
        component.findElement(zipCodeSel).sendKeys(zipcode);
    }

    public void inputPhone(String phone) {
        component.findElement(phoneNumberSel).sendKeys(phone);
    }

    public void clickOnContinueBtn() {
        WebElement continueBtnElem = component.findElement(continueBtnSel);
        continueBtnElem.click();
        wait.until(ExpectedConditions.invisibilityOf(continueBtnElem));
    }
}
