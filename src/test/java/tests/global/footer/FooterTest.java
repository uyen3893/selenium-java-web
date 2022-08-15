package tests.global.footer;

import driver.DriverFactory;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import test_flows.global.FooterTestFlow;
import tests.BaseTest;
import url.Urls;

public class FooterTest extends BaseTest {

    @Test()
    private static void testFooterRegisterPage() {
        driver.get(Urls.demoBaseUrl);
        Assert.fail("Demo taking screenshot when test is failed!");
    }

   @Test()
    private static void testFooterLoginPage() {

    }

    @Test
    private static void testFooterCategoryPage() {
        driver.get(Urls.demoBaseUrl);
        FooterTestFlow footerTestFlow = new FooterTestFlow(driver);
        footerTestFlow.verifyProductCateFooterComponent();

    }

    @Test()
     public void testFooterHomePage() {

    }


}
