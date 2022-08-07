package test_flows.global;

import models.components.global.footer.FooterColumnComponent;
import models.components.global.footer.FooterComponent;
import models.pages.BasePage;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import url.Urls;

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
