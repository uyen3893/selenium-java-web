package test_flows.computer;

import models.components.order.ComputerEssentialComponent;
import models.pages.ComputerItemDetailsPage;
import org.openqa.selenium.WebDriver;
import test_data.computer.ComputerData;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderComputerFlow<T extends ComputerEssentialComponent> {

    private final WebDriver driver;
    private final Class<T> computerEssentialComponent;
    private final ComputerData computerData;

    public OrderComputerFlow(WebDriver driver, Class<T> computerEssentialComponent, ComputerData computerData) {
        this.driver = driver;
        this.computerEssentialComponent = computerEssentialComponent;
        this.computerData = computerData;
    }

    public void buildComputerSpecAndAddToCart() {
        ComputerItemDetailsPage computerItemDetailsPage = new ComputerItemDetailsPage(driver);
        T computerEssentialComp = computerItemDetailsPage.computerComp(computerEssentialComponent);

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
}
