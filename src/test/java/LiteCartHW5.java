import businessobjects.CheckoutBO;
import businessobjects.MainPageBO;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class LiteCartHW5 extends BaseTest {
    private final WebDriverWait wait = new WebDriverWait(driver, 10);
    public static final String expectedMessage = "There are no items in your cart.";
    private MainPageBO mainPageBO = new MainPageBO(driver);
    private CheckoutBO checkoutBO = new CheckoutBO(driver);

    @Test
    void cartTest() {
        mainPageBO.openMainPage();
        mainPageBO.addRandomProductsToCart(3);
        checkoutBO.removeAllProductsFromCart();
        String actualMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content > p"))).getText();
        Assert.assertEquals("Messages are equals: ", actualMessage, expectedMessage);

    }
}
