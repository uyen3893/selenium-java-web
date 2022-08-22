package test_flows.computer;

import models.components.cart.TotalComponent;
import models.components.order.ComputerEssentialComponent;
import models.pages.ComputerItemDetailsPage;
import models.pages.ShoppingCartPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import test_data.computer.ComputerData;

import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerData computerData;
    private final int quantity;
    private double totalPrice;

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
        TotalComponent totalComponent = shoppingCartPage.totalComponent();
        Map<String, Double> priceCategories = totalComponent.priceCategories();
        for (String priceType : priceCategories.keySet()) {
            System.out.println(priceType + ": " + priceCategories.get(priceType));
        }
    }
}
