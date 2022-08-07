package tests.global.footer;

import driver.DriverFactory;
import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.InformationColumnComponent;
import models.pages.HomePage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import support.verification.Verifier;
import test_flows.global.FooterTestFlow;
import url.Urls;

public class FooterTest {

    @Test()
    private static void testFooterRegisterPage() {

    }

   @Test()
    private static void testFooterLoginPage() {

    }

    @Test
    private static void testFooterCategoryPage() {
        WebDriver driver = DriverFactory.getChromeDriver();
        try {
            driver.get(Urls.demoBaseUrl);
            FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
            footerTestFlow.verifyProductCateFooterComponent();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }

    }

    @Test()
     public void testFooterHomePage() {

    }


}
