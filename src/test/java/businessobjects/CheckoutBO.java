package businessobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.CheckoutPage;

import static constants.Constants.*;

public class CheckoutBO {
    private CheckoutPage checkoutPage;
    private WebDriver driver;

    public CheckoutBO(WebDriver driver) {
        this.checkoutPage = new CheckoutPage(driver);
        this.driver = driver;
    }

    public void openCart() {
        checkoutPage.open();
        checkoutPage.wait.until(ExpectedConditions.visibilityOf(checkoutPage.getHeader()));
    }


    public void removeAllProductsFromCart() {
        if (!driver.getCurrentUrl().equalsIgnoreCase(CHECKOUT_PAGE_URL)) {
            openCart();
        }
        int countOfItems = checkoutPage.getProducts().size();
        while (countOfItems != 0) {
            new CheckoutPage(driver).getProducts().get(countOfItems - 1).findElement(By.cssSelector(".btn.btn-danger")).click();
            if (countOfItems != 1) {
                waitUtilItemRemoved();
            }
            --countOfItems;
        }
    }

    private void waitUtilItemRemoved() {
        checkoutPage.wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("body > .loader-wrapper")));
        checkoutPage.wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body > .loader-wrapper")));
    }

}
