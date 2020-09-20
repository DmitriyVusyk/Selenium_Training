import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.List;
import java.util.stream.Collectors;

import static constants.Constants.*;

public class LiteCartHW3 extends BaseTest {
    private static final String INPUT_FIELD_USERNAME_XPATH = "//*[@class='form-control' and @name='username']";
    private static final String INPUT_FIELD_PASSWORD_XPATH = "//*[@class='form-control' and @name='password']";
    private static final String BTN_LOGIN_XPATH = "//*[@class='btn btn-default'][@value='Login']";
    private static final String SIDEBAR_CSS_SELECTOR = "#box-apps-menu";
    private static final String HEADER_CSS_SELECTOR = "div.panel-heading";
    private WebDriverWait wait = new WebDriverWait(driver, 10);

    @Tag("Verify-that-every-section-on-admin-page-has-header")
    @Test
    @Order(1)
    void everySectionHasHeader() {
        driver.get(LITE_CART_LOGIN_FORM_URL);
        login(USERNAME, PASSWORD);
        List<String> sideBarElementsLinks = driver.findElements(By.xpath("//*[@id='box-apps-menu']//li[@class='app']//a"))
                .stream()
                .map(webElement -> webElement.getAttribute("href"))
                .collect(Collectors.toList());
        sideBarElementsLinks.forEach(link -> {
            String menuItemXpath = "//*[@id='box-apps-menu']//li[@class='app']//a[@href='" + link + "']";
            wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(menuItemXpath)));
            WebElement menuItem = driver.findElement(By.xpath(menuItemXpath));
            menuItem.click();
            List<String> subMenuItemLinks = driver.findElements(By.cssSelector("#box-apps-menu .doc a"))
                    .stream()
                    .map(webElement -> webElement.getAttribute("href"))
                    .collect(Collectors.toList());
            if (subMenuItemLinks.size() != 0) {
                subMenuItemLinks.forEach(subItemLink -> {
                    String subMenuItemCSSSelector = "#box-apps-menu .doc a[href*='" + subItemLink + "']";
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(subMenuItemCSSSelector)));
                    WebElement subMenuItem = driver.findElement(By.cssSelector(subMenuItemCSSSelector));
                    subMenuItem.click();
                    Assert.assertTrue(driver.findElement(By.cssSelector(HEADER_CSS_SELECTOR)).isDisplayed());
                    System.out.println("Assertion for menu item: " + link + "; sub menu item: " + subItemLink);
                });
            } else {
                Assert.assertTrue(driver.findElement(By.cssSelector(HEADER_CSS_SELECTOR)).isDisplayed());
                System.out.println("Assertion for menu item: " + link);
            }
        });
    }

    @Tag("Verify that properties for the product is correctly displayed on both main and product pages")
    @Test
    @Order(2)
    void checkProperties() {
        driver.get(GL_SELENIUM_DEMO_STORE_URL);
        WebElement campaignFirstProductContainerMainPage = driver.findElement(By.xpath("//*[@id='box-campaign-products']//div[@class='listing products']/article"));
        WebElement priceWrapper = campaignFirstProductContainerMainPage.findElement(By.xpath("//div[@class='price-wrapper']"));
        WebElement regularPrice = priceWrapper.findElement(By.xpath("//del[@class='regular-price']"));
        WebElement campaignPrice = priceWrapper.findElement(By.xpath("//strong[@class='campaign-price']"));

        String expectedName = campaignFirstProductContainerMainPage.findElement(By.xpath("//h4[@class='name']")).getText();
        String expectedRegularPrice = regularPrice.getText();
        String expectedCampaignPrice = campaignPrice.getText();
        String expectedRegularColor = regularPrice.getCssValue("color");
        String expectedCampaignColor = campaignPrice.getCssValue("color");
        String expectedRegularPhontDecoration = regularPrice.getCssValue("text-decoration-line");
        String expectedCampaignPhont = campaignPrice.getCssValue("font-weight");
        campaignFirstProductContainerMainPage.click();


        WebElement productPageView = driver.findElement(By.xpath("//*[@id='box-product']"));
        WebElement productPagePriceWrapper = productPageView.findElement(By.xpath("//div[@class='price-wrapper']"));
        WebElement productPageRegularPrice = productPagePriceWrapper.findElement(By.xpath("//del[@class='regular-price']"));
        WebElement productPageCampaignPrice = productPagePriceWrapper.findElement(By.xpath("//strong[@class='campaign-price']"));

        String actualName = productPageView.findElement(By.xpath("//h1[@class='title']")).getText();
        String actualRegularPrice = productPageRegularPrice.getText();
        String actualCampaignPrice = productPageCampaignPrice.getText();
        String actualRegularColor = productPageRegularPrice.getCssValue("color");
        String actualCampaignColor = productPageCampaignPrice.getCssValue("color");
        String actualRegularPhontDecoration = productPageRegularPrice.getCssValue("text-decoration-line");
        String actualCampaignPhont = productPageCampaignPrice.getCssValue("font-weight");

        Assert.assertEquals("Verify that Product Name is the same on Main page and on Item Page",
                expectedName, actualName);
        Assert.assertEquals("Verify that discounted prices are equal on both pages",
                actualCampaignPrice, expectedCampaignPrice);
        Assert.assertEquals("Verify that regular prices are equal on both pages",
                actualRegularPrice, expectedRegularPrice);
        Assert.assertEquals("Verify that regular price is gray on both pages",
                actualRegularColor, expectedRegularColor);
        Assert.assertEquals("Verify that regular price is strike on both pages",
                actualRegularPhontDecoration, expectedRegularPhontDecoration);
        Assert.assertEquals("Verify that campaigns price is red on both pages",
                actualCampaignColor, expectedCampaignColor);
        Assert.assertEquals("Verify that campaigns price is bold on both pages",
                actualCampaignPhont, expectedCampaignPhont);
    }

    void login(String username, String password) {
        WebElement ifUsername = driver.findElement(By.xpath(INPUT_FIELD_USERNAME_XPATH));
        ifUsername.sendKeys(username);
        WebElement ifPassword = driver.findElement(By.xpath(INPUT_FIELD_PASSWORD_XPATH));
        ifPassword.sendKeys(password);
        WebElement btnSubmit = driver.findElement(By.xpath(BTN_LOGIN_XPATH));
        btnSubmit.click();
        WebElement sidebar = driver.findElement(By.cssSelector(SIDEBAR_CSS_SELECTOR));
        wait.until(ExpectedConditions.visibilityOf(sidebar));
        Assert.assertTrue(sidebar.isDisplayed());
    }
}
