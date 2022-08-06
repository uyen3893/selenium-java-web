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
import url.Urls;

public class FooterTest {

    @Test(priority = 1)
    private static void testFooterRegisterPage() {
        String actualResult = "actual";
        String expectedResult = "expected";

//        Verifier.verifyEquals(actualResult, expectedResult);
        //Hard assertion
        Assert.assertEquals(actualResult, expectedResult, "[ERR] Welcome message is incorrect!");
        Assert.assertTrue(actualResult.equals(expectedResult));
        Assert.assertFalse(actualResult.equals(expectedResult));
        Assert.fail();
        Assert.fail("...");

    }

   @Test(priority = 2)
    private static void testFooterLoginPage() {
        //Soft assertion
       SoftAssert softAssert = new SoftAssert();
       softAssert.assertEquals(2, 3);
       softAssert.assertEquals(1, 1);
       softAssert.assertEquals(5, 7);

       System.out.println("Successfully");
       softAssert.assertAll();

    }

    @Test(dependsOnMethods = {"testFooterRegisterPage"})
    private static void testFooterCategoryPage() {
    }

    @Test(priority = 3)
     public void testFooterHomePage() {
        WebDriver driver = DriverFactory.getChromeDriver();

        try {
            driver.get(Urls.demoBaseUrl);
            HomePage homePage = new HomePage(driver);
            InformationColumnComponent informationColumnComponent = homePage.footerComponent().informationColumnComponent();

            testFooterColumn(informationColumnComponent);
        } catch (Exception ignored) {}

        driver.quit();

    }

    private void testFooterColumn(FooterColumnComponent footerColumnComponent) {
        System.out.println(footerColumnComponent.headerElem().getText());
        footerColumnComponent.linkElems().forEach(link -> {
            System.out.println(link.getText());
            System.out.println(link.getAttribute("href"));
        });
    }
}
