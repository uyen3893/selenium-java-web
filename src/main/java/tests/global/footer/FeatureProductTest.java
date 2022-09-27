package tests.global.footer;

import driver.DriverFactory;
import models.components.product.ProductItemComponent;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import url.Urls;

import java.util.List;

public class FeatureProductTest {

    public static void main(String[] args) {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(Urls.demoBaseUrl);
            verifyFeaturedProductHomePage(driver);
        } catch (Exception e) {
            e.printStackTrace();
        }

        driver.quit();
    }
     public static void verifyFeaturedProductHomePage(WebDriver driver) {
        HomePage homePage = new HomePage(driver);
        List<ProductItemComponent> productItemComponentList = homePage.productGridComponent().productItemComponentList();

        productItemComponentList.forEach(productItemComponent -> {
            System.out.println(productItemComponent.productTitleElem().getText());
        });
    }


}
