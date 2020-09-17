import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

import static constants.Constants.GL_SELENIUM_DEMO_STORE_URL;

public class LiteCartTest extends BaseTest {

    @BeforeEach
    void beforeTest() {
        driver.get(GL_SELENIUM_DEMO_STORE_URL);
    }

    //Homework task 1 start
    @Tag("Find image of discounted yellow duck from popular products by xpath with sale validation")
    @Test
    @Order(1)
    void popularDiscountedDuckXPath() {
        WebElement yellowDuckImage = driver.findElement(By.xpath("//*[@id=\"box-popular-products\"]//*[@class=\"sticker sale\" and text()=\"Sale\"]/ancestor::article//img[contains(@alt,\"Yellow Duck\")]"));
        Assert.assertTrue(yellowDuckImage.isDisplayed());
    }

    @Tag("Find image of discounted yellow duck from popular products by css without sale validation")
    @Test
    @Order(2)
    void popularDiscountedDuckSimpleCSS() {
        WebElement yellowDuckImage = driver.findElement(By.cssSelector("#box-popular-products img[class*='img-responsive'][alt='Yellow Duck']"));
        Assert.assertTrue(yellowDuckImage.isDisplayed());
    }

    @Tag("Find image of discounted yellow duck images from list of popular products by css")
    @Test
    @Order(3)
    void popularDiscountedDuckCSS() {
        List<WebElement> yellowDuckImages = driver.findElements(By.cssSelector("#box-popular-products .product-column"));
        yellowDuckImages.stream().filter(webElement -> webElement.getText().equalsIgnoreCase("on sale"));
        yellowDuckImages.forEach(webElement -> Assert.assertTrue("Yellow duck image is present: ", webElement.isDisplayed()));
    }
    //Homework task 1 end
    //Homework task 2 start
    @Tag("Find image of discounted yellow duck by xpath with sale validation")
    @Test
    @Order(4)
    void latestProductsDiscountedDuckXPath() {
        WebElement yellowDuckImage = driver.findElement(By.xpath("//*[@id=\"box-latest-products\"]//*[@class=\"sticker sale\" and text()=\"Sale\"]/ancestor::article//img[contains(@alt,\"Yellow Duck\")]"));
        Assert.assertTrue(yellowDuckImage.isDisplayed());
    }

    @Tag("Find image of discounted yellow duck by css without sale validation")
    @Test
    @Order(5)
    void latestProductsDiscountedDuckSimpleCSS() {
        WebElement yellowDuckImage = driver.findElement(By.cssSelector("#box-latest-products img[class*='img-responsive'][alt='Yellow Duck']"));
        Assert.assertTrue(yellowDuckImage.isDisplayed());
    }
    //Homework task 2 end
    //Homework task 3 start
    @Tag("Find image of discounted yellow duck from popular products by xpath with sale validation")
    @Test
    @Order(6)
    void rubberDucksLinkVisible() {
        openYellowDuckItemPageByCssSelector("#box-popular-products img[class*='img-responsive'][alt='Yellow Duck']");
        WebElement rubberDucksLink = driver.findElement(By.xpath("//*[@class='breadcrumb']//a[@href='http://35.236.6.102/litecart/rubber-ducks-c-1/' and 'text()=Rubber Ducks']"));
        Assert.assertTrue(rubberDucksLink.isDisplayed());
    }

    private void openYellowDuckItemPageByCssSelector(String sccSelector){
        WebElement itemContainer = driver.findElement(By.cssSelector(sccSelector));
        itemContainer.click();
        WebElement productBox = driver.findElement(By.cssSelector("article[id='box-product']"));
        Assert.assertTrue(productBox.isDisplayed());
    }
}
