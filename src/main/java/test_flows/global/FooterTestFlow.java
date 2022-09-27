package test_flows.global;

import models.components.global.TopMenuComponent;
import static models.components.global.TopMenuComponent.MainCateItemComponent;
import static models.components.global.TopMenuComponent.CateItemComponent;
import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.FooterComponent;
import models.pages.BasePage;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import url.Urls;

import java.security.SecureRandom;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FooterTestFlow {

    private WebDriver driver;

    public FooterTestFlow(WebDriver driver) {
        this.driver = driver;
    }

    public void verifyFooterComponent() {
        BasePage basePage = new BasePage(driver);
        FooterComponent footerComponent = basePage.footerComponent();
        verifyInformationColumn(footerComponent.informationColumnComponent());
        verifyCustomerServiceColumn(footerComponent.customerServiceColumnComponent());
        verifyAccountColumn(footerComponent.myAccountColumnComponent());
        verifyFollowUsColumn(footerComponent.followUsColumnComponent());
    }

    private void verifyFollowUsColumn(FooterColumnComponent footerColumnComponent) {
        String url = Urls.demoBaseUrl;
        List<String> expectedLinkTexts = Arrays.asList(
                "Facebook", "Twitter", "RSS", "YouTube", "Google+"
        );
        List<String> expectedHrefs = Arrays.asList(
                "http://www.facebook.com/nopCommerce", "https://twitter.com/nopCommerce",
                url + "/news/rss/1", "http://www.youtube.com/user/nopCommerce", "https://plus.google.com/+nopcommerce"
        );
        testFooterColumn(footerColumnComponent, expectedLinkTexts, expectedHrefs);
    }

    private void verifyAccountColumn(FooterColumnComponent footerColumnComponent) {
        String url = Urls.demoBaseUrl;
        List<String> expectedLinkTexts = Arrays.asList(
                "My account", "Orders", "Addresses", "Shopping cart", "Wishlist");
        List<String> expectedHrefs = Arrays.asList(
                url + "/customer/info", url + "/customer/orders", url + "/customer/addresses",
                url + "/cart", url + "/wishlist"
        );
        testFooterColumn(footerColumnComponent, expectedLinkTexts, expectedHrefs);
    }

    private void verifyCustomerServiceColumn(FooterColumnComponent footerColumnComponent) {
        String url = Urls.demoBaseUrl;
        List<String> expectedLinkTexts = Arrays.asList(
                "Search", "News", "Blog", "Recently viewed products",
                "Compare products list", "New products");
        List<String> expectedHrefs = Arrays.asList(
                url + "/search", url + "/news", url + "/blog", url + "/recentlyviewedproducts",
                url + "/compareproducts", url + "/newproducts");
        testFooterColumn(footerColumnComponent, expectedLinkTexts, expectedHrefs);
    }

    private void verifyInformationColumn(FooterColumnComponent footerColumnComponent) {
        String url = Urls.demoBaseUrl;
        List<String> expectedLinkTexts = Arrays.asList(
                "Sitemap", "Shipping & Returns", "Privacy Notice", "Conditions of Use",
                "About us", "Contact us");
        List<String> expectedHrefs = Arrays.asList(
                url + "/sitemap", url + "/shipping-returns", url + "/privacy-policy", url + "/conditions-of-use",
                url + "/about-us", url + "/contactus");
        testFooterColumn(footerColumnComponent, expectedLinkTexts, expectedHrefs);
    }

    public void verifyProductCateFooterComponent() {
        BasePage basePage = new BasePage(driver);
        TopMenuComponent topMenuComponent = basePage.topMenuComponent();
        //Get a list of menu category
        List<MainCateItemComponent> mainCateItemComponentList = topMenuComponent.mainItemsElem();
        if(mainCateItemComponentList.isEmpty()) {
            Assert.fail("[ERR] Top menu items are empty!");
        }

        //Random select a category
        MainCateItemComponent randomMainItemElem = mainCateItemComponentList.get(
                new SecureRandom().nextInt(mainCateItemComponentList.size()));

        String randomCatHref = randomMainItemElem.catItemLinkElem().getAttribute("href");

        //Get a list of items from a selected category
        List<CateItemComponent> cateItemComponentList = randomMainItemElem.cateItemComponentList();
        //Click on Link if the list of items is empty
        if(cateItemComponentList.isEmpty()) {
            randomMainItemElem.catItemLinkElem().click();
        } else {
            //Click randomly on item on the list
            int randomIndex = new SecureRandom().nextInt(cateItemComponentList.size());
            CateItemComponent randomCateItemComp = cateItemComponentList.get(randomIndex);
            randomCatHref = randomCateItemComp.getComponent().getAttribute("href");
            randomCateItemComp.getComponent().click();
        }

        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.urlContains(randomCatHref));
        } catch (TimeoutException e) {
            Assert.fail("[ERR] Target page is not matched!");
        }

        verifyFooterComponent();
    }

    private void testFooterColumn(
            FooterColumnComponent footerColumnComponent, List<String> expectedLinkTexts, List<String> expectedHrefs) {

        List<String> actualLinkTexts = new ArrayList<>();
        List<String> actualHrefs = new ArrayList<>();

        for (WebElement linkElem : footerColumnComponent.linkElems()) {
            actualLinkTexts.add(linkElem.getText().trim());
            actualHrefs.add(linkElem.getAttribute("href"));
        }

        //Verify link texts and hyperlinks are empty or not
        if (actualHrefs.isEmpty() || actualLinkTexts.isEmpty()) {
            Assert.fail("[ERR] Texts or hyperlinks are empty!");
        }

        //Verify link texts
        Assert.assertEquals(actualLinkTexts.size(), expectedLinkTexts.size(),
                "[ERR] Actual and expected texts are different!");


        //Verify hyperlinks
        Assert.assertEquals(actualHrefs.size(), expectedHrefs.size(),
                "[ERR] Actual and expected hyperlinks are different!");
    }

}
