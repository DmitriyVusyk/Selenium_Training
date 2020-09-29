package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

import static constants.Constants.MAIN_PAGE_URL;

public class MainPage extends BasePage {

    @FindBy(id = "header")
    private WebElement header;

    @FindBy(css = "#box-popular-products .product-column")
    private List<WebElement> popularProducts;

    @FindBy(css = ".badge.quantity")
    private WebElement cartButton;

    public MainPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getHeader() {
        return header;
    }

    public List<WebElement> getPopularProducts() {
        return popularProducts;
    }

    public WebElement getCartButton() {
        return cartButton;
    }

    @Override
    public void open() {
        driver.get(MAIN_PAGE_URL);
    }

    public void clickOnItem(WebElement item) {
        wait.until(ExpectedConditions.elementToBeClickable(item.findElement(By.cssSelector(".link")))).click();
    }
}
