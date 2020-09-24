import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


import static constants.Constants.GL_SELENIUM_DEMO_STORE_URL;

public class LiteCartHomeWork5 extends BaseTest {
    private final WebDriverWait wait = new WebDriverWait(driver, 10);
    public static final String expectedMessage = "There are no items in your cart.";

    @Test
    void cartTest() {
        addRandomProduct(3);
        openCart();
        removeAllProductsFromCart();
        String actualMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#content > p"))).getText();
        Assert.assertEquals("Messages are equals: ", actualMessage, expectedMessage);
    }

    private void addRandomProduct(int count) {
        driver.get(GL_SELENIUM_DEMO_STORE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-popular-products .link"))).click();
        String countOfExpectedItems = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".badge.quantity"))).getText();
        int itemsInCart;
        if (!countOfExpectedItems.equals("")){
            itemsInCart = Integer.parseInt(countOfExpectedItems);
        } else {
            itemsInCart = 1;
        }
        for (int i = itemsInCart; i <= count; i++) {
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".btn.btn-success"))).click();
            wait.until(ExpectedConditions.textToBe(By.cssSelector(".badge.quantity"), String.valueOf(i)));
        }
    }

    private void removeAllProductsFromCart() {
        String inputXpath = "//div[@class='table-responsive']//input[@class='form-control']";
        WebElement input = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(inputXpath)));
        int countOfItems = Integer.parseInt(input.getAttribute("value"));
        while (countOfItems != 0) {
            --countOfItems;
            WebElement inputField = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(inputXpath)));
            inputField.clear();
            inputField.sendKeys(String.valueOf(countOfItems));
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[@name='update_cart_item']")))
                    .click();
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("body > .loader-wrapper")));
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("body > .loader-wrapper")));
        }

    }

    private void openCart() {
        driver.get(GL_SELENIUM_DEMO_STORE_URL);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".badge.quantity"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("#box-checkout-payment")));
    }
}
