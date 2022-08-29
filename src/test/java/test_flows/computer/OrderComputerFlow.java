package test_flows.computer;

import models.components.cart.CartItemRowComponent;
import models.components.cart.TotalComponent;
import models.components.checkout.*;
import models.components.order.ComputerEssentialComponent;
import models.pages.CheckoutOptionsPage;
import models.pages.CheckoutPage;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.CreditCardType;
import test_data.DataObjectBuilder;
import test_data.PaymentMethod;
import test_data.computer.ComputerData;
import test_data.user.UserDataObject;

import java.security.SecureRandom;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerData computerData;
    private final int quantity;
    private double totalPrice;
    private UserDataObject defaultCheckoutUser;
    private PaymentMethod paymentMethod;
    private CreditCardType creditCardType;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerData computerData) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerData = computerData;
        this.quantity = 1;
    }

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerData computerData, int quantity) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerData = computerData;
        this.quantity = quantity;
    }

    public void buildComputerSpecAndAddToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        T computerEssentialComp = computerItemDetailsPage.computerComp(computerEssentialComponent);

        //Unchecked all options
        computerEssentialComp.uncheckedDefaultOptions();

        String processorFullStr = computerEssentialComp.selectProcessorType(computerData.getProcessorType());
        double processorAdditionalPrice = extractAdditionalString(processorFullStr);
        System.out.println("processorAdditionalPrice: " + processorAdditionalPrice);

        String ramFullStr = computerEssentialComp.selectRAMType(computerData.getRam());
        double ramAdditionalPrice = extractAdditionalString(ramFullStr);
        System.out.println("ramAdditionalPrice: " + ramAdditionalPrice);

        String hddFullStr = computerEssentialComp.selectHDDType(computerData.getHdd());
        double hddAdditionalPrice = extractAdditionalString(hddFullStr);
        System.out.println("hddAdditionalPrice: " + hddAdditionalPrice);

        String softwareFullStr = computerEssentialComp.selectSoftwareType(computerData.getSoftware());
        double softwareAdditionalPrice = extractAdditionalString(softwareFullStr);
        System.out.println("softwareAdditionalPrice: " + softwareAdditionalPrice);

        double osAdditionalPrice = 0;
        if (computerData.getOs() != null) {
            String osFullStr = computerEssentialComp.selectOsType(computerData.getOs());
            osAdditionalPrice = extractAdditionalString(osFullStr);
            System.out.println("osAdditionalPrice: " + osAdditionalPrice);
        }

        //Caculate item's price
        double basePrice = computerEssentialComp.productPrice();
        System.out.println("basePrice: " + basePrice);
        double allAdditionalPrice =
                processorAdditionalPrice + ramAdditionalPrice + osAdditionalPrice + hddAdditionalPrice + softwareAdditionalPrice;
        totalPrice = (basePrice + allAdditionalPrice) * quantity;
        System.out.println("totalPrice: " + totalPrice);

        //Add to cart
        computerEssentialComp.clickAddToCart();
        computerEssentialComp.waitUntilItemAddedToCart();

        //Navigate to the shopping cart
        computerItemDetailsPage.headerComponent().clickOnShoppingCartLink();

    }

    private double extractAdditionalString(String itemStr){
        double price = 0;
        Pattern pattern = Pattern.compile("\\[(.*?)\\]");
        Matcher matcher = pattern.matcher(itemStr);
        if (matcher.find()) {
            price = Double.parseDouble(matcher.group(1));
        }
        return price;
    }

    public void verifyShoppingCartPage() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        List<CartItemRowComponent> cartItemRowComps = shoppingCartPage.cartItemRowComponentList();
        if (cartItemRowComps.isEmpty()) {
            Assert.fail("[ERR] There is no item displayed in the shopping cart!");
        }
        double currentSubTotal = 0;
        double currentTotalUnitPrice = 0;

        for (CartItemRowComponent cartItemRowComp : cartItemRowComps) {
            currentSubTotal += cartItemRowComp.getSubTotal();
            currentTotalUnitPrice += (cartItemRowComp.getUnitPrice() * cartItemRowComp.getItemQuantity());
        }

        Assert.assertEquals(currentSubTotal, currentTotalUnitPrice, " [ERR] Shopping cart's sub-total is incorrect!");

        TotalComponent totalComponent = shoppingCartPage.totalComponent();
        double checkoutSubTotal = 0;
        double checkoutOtherFeesTotal = 0;
        double checkoutTotal = 0;

        Map<String, Double> priceCategories = totalComponent.priceCategories();
        for (String priceType : priceCategories.keySet()) {
            System.out.println(priceType + ": " + priceCategories.get(priceType));
        }

        for (String priceType : priceCategories.keySet()) {
            if (priceType.startsWith("Sub-Total")) {
                checkoutSubTotal = priceCategories.get(priceType);
            } else if (priceType.startsWith("Total")) {
                checkoutTotal = priceCategories.get(priceType);
            } else {
                checkoutOtherFeesTotal += priceCategories.get(priceType);
            }
        }

        Assert.assertEquals(checkoutSubTotal, currentSubTotal, "[ERR] Shopping cart's sub total is incorrect!");
        Assert.assertEquals(checkoutTotal, (checkoutSubTotal + checkoutOtherFeesTotal), "[ERR] Shopping cart's total is incorrect!");
    }

    public void agreeTOSAndCheckout() {
        ShoppingCartPage shoppingCartPage = new ShoppingCartPage(driver);
        TotalComponent totalComponent = shoppingCartPage.totalComponent();
        totalComponent.agreeTOS();
        totalComponent.checkout();

        CheckoutOptionsPage checkoutOptionsPage = new CheckoutOptionsPage(driver);
        checkoutOptionsPage.checkoutAsGuest();
    }

    public void inputBillingAddress() {
        String defaultCheckoutUserJSONLoc = "/src/test/java/test_data/user/DefaultCheckoutUser.json";
        defaultCheckoutUser = DataObjectBuilder.buildDataObjectFrom(defaultCheckoutUserJSONLoc, UserDataObject.class);
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        BillingAddressComponent billingAddressComp = checkoutPage.billingAddressComponent();
        //Fill in the form
        billingAddressComp.selectBillingAddress();
        billingAddressComp.inputFirstName(defaultCheckoutUser.getFirstName());
        billingAddressComp.inputlastName(defaultCheckoutUser.getLastName());
        billingAddressComp.inputEmail(defaultCheckoutUser.getEmail());
        billingAddressComp.selectCountry(defaultCheckoutUser.getCountry());
        billingAddressComp.selectState(defaultCheckoutUser.getState());
        billingAddressComp.inputCity(defaultCheckoutUser.getCity());
        billingAddressComp.inputAddress1(defaultCheckoutUser.getAddress1());
        billingAddressComp.inputZipCode(defaultCheckoutUser.getZipcode());
        billingAddressComp.inputPhone(defaultCheckoutUser.getPhoneNum());

        billingAddressComp.clickOnContinueBtn();

    }

    public void inputShippingAddress() {
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        ShippingAddressComponent shippingAddressComponent = checkoutPage.shippingAddressComponent();
        shippingAddressComponent.clickOnContinueBtn();
    }

    public void inputShippingMethod() {
        List<String> shippingMethods = Arrays.asList("Ground", "Next Day Air", "2nd Day Air");
        String randomShippingMethod = shippingMethods.get(new SecureRandom().nextInt(shippingMethods.size()));
        CheckoutPage checkoutPage = new CheckoutPage(driver);
        ShippingMethodComponent shippingMethodComponent = checkoutPage.shippingMethodComponent();
        shippingMethodComponent.selectShippingMethod(randomShippingMethod);
        shippingMethodComponent.clickOnContinueBtn();
    }

    public void selectPaymentMethod() {
        this.paymentMethod = PaymentMethod.COD;

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentMethodComponent paymentMethodComponent = checkoutPage.paymentMethodComponent();
        paymentMethodComponent.selectCODMethod();
        paymentMethodComponent.clickOnContinueBtn();
    }

    public void selectPaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == null) {
            throw new IllegalArgumentException("[ERR] Payment method is not null!");
        }
        this.paymentMethod = paymentMethod;

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentMethodComponent paymentMethodComponent = checkoutPage.paymentMethodComponent();

        switch (paymentMethod) {
            case CHECK_MONEY_ORDER:
                paymentMethodComponent.selectCheckMoneyOrderMethod();
                break;
            case CREDIT_CARD:
                paymentMethodComponent.selectCreditCardMethod();
                break;
            case PURCHASE_ORDER:
                paymentMethodComponent.selectPurchaseOrderMethod();
                break;
            default:
                paymentMethodComponent.selectCODMethod();
        }

        paymentMethodComponent.clickOnContinueBtn();
    }

    public void inputPaymentInfo(CreditCardType creditCardType) {
        if (creditCardType == null) {
            throw new IllegalArgumentException("[ERR] Credit card is not null!");
        }
        this.creditCardType = creditCardType;

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        PaymentInformationComponent paymentInformationComponent = checkoutPage.paymentInformationComponent();

        if (this.paymentMethod.equals(PaymentMethod.CREDIT_CARD)) {
            paymentInformationComponent.selectCardType(creditCardType);
            paymentInformationComponent.inputCardHolderName(
                    this.defaultCheckoutUser.getFirstName() + " " + this.defaultCheckoutUser.getLastName());

            String cardNumber = this.creditCardType.equals(CreditCardType.VISA) ? "4012888888881881" : "6011000990139424";
            paymentInformationComponent.inputCardNumber(cardNumber);

            Calendar calendar = new GregorianCalendar();
            paymentInformationComponent.selectExpiredMonth(String.valueOf(calendar.get(Calendar.MONTH) + 1));
            paymentInformationComponent.selectExpiredYear(String.valueOf(calendar.get(Calendar.YEAR) + 1));
            paymentInformationComponent.inputCardCode("123");
        } else if (this.paymentMethod.equals(PaymentMethod.PURCHASE_ORDER)) {
            paymentInformationComponent.inputPurchaseOrderNumber("123");
        } else if (this.paymentMethod.equals(PaymentMethod.COD)) {
            paymentInformationComponent.verifySelectCODOption();
        } else {
            paymentInformationComponent.verifySelectCheckMoneyOrderOption();
        }

        paymentInformationComponent.clickOnContinueBtn();
    }

    public void confirmOrder() {
        Assert.fail();
    }
}
