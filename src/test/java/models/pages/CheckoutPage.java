package models.pages;

import models.components.checkout.*;
import org.openqa.selenium.WebDriver;

import java.util.List;

public class CheckoutPage extends BasePage{

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public BillingAddressComponent billingAddressComponent() {
        return findComponent(BillingAddressComponent.class, driver);
    }

    public ShippingAddressComponent shippingAddressComponent() {
        return findComponent(ShippingAddressComponent.class, driver);
    }

    public ShippingMethodComponent shippingMethodComponent() {
        return findComponent(ShippingMethodComponent.class, driver);
    }

    public PaymentMethodComponent paymentMethodComponent() {
        return findComponent(PaymentMethodComponent.class, driver);
    }

    public PaymentInformationComponent paymentInformationComponent() {
        return findComponent(PaymentInformationComponent.class, driver);
    }

    public ConfirmOrderComponent confirmOrderComponent() {
        return findComponent(ConfirmOrderComponent.class, driver);
    }

    public BillingInfoComponent billingInfoComponent() {
        return findComponent(BillingInfoComponent.class, driver);
    }

    public ShippingInfoComponent shippingInfoComponent() {
        return findComponent(ShippingInfoComponent.class, driver);
    }

    public List<CartItemRowsComponent> cartItemRowsComponent() {
        return findComponents(CartItemRowsComponent.class, driver);
    }
}
