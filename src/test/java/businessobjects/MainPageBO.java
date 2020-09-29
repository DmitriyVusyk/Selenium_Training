package businessobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.MainPage;
import pageobjects.ProductPage;

import java.util.List;

import static constants.Constants.MAIN_PAGE_URL;

public class MainPageBO {
    private MainPage mainPage;
    private WebDriver driver;
    private ProductPage productPage;

    public MainPageBO(WebDriver driver) {
        this.mainPage = new MainPage(driver);
        this.driver = driver;
        this.productPage = new ProductPage(driver);
    }

    public void openMainPage() {
        mainPage.open();
        mainPage.wait.until(ExpectedConditions.visibilityOf(mainPage.getHeader()));
    }

    public void addRandomProductsToCart(int count) {
        if (!driver.getCurrentUrl().equalsIgnoreCase(MAIN_PAGE_URL)) {
            openMainPage();
        }
        mainPage = new MainPage(driver);
        String countOfExpectedItems = mainPage.getCartButton().getText();
        int itemsInCart;
        if (!countOfExpectedItems.equals("")) {
            itemsInCart = Integer.parseInt(countOfExpectedItems);
        } else {
            itemsInCart = 1;
        }
        for (int i = itemsInCart; i <= count; i++) {
            mainPage.open();
            List<WebElement> popularProducts = mainPage.getPopularProducts();
            int itemIndex = (int) (Math.random() * popularProducts.size() - 1);
            WebElement item = popularProducts.get(itemIndex);
            mainPage.clickOnItem(item);
            productPage = new ProductPage(driver);
            productPage.getAddToCartButton().click();
            productPage.wait
                    .until(
                            ExpectedConditions.textToBePresentInElement(
                                    productPage.getCartButton(), String.valueOf(i)));
        }

    }

}
