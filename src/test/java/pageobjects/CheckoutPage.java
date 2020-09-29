package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

import static constants.Constants.CHECKOUT_PAGE_URL;

public class CheckoutPage extends BasePage {

    @FindBy(id = "header")
    private WebElement header;

    @FindBy(css = ".table-responsive .item")
    private List<WebElement> products;

    public CheckoutPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @Override
    public void open() {
        driver.get(CHECKOUT_PAGE_URL);
    }

    public List<WebElement> getProducts() {
        return products;
    }

    public WebElement getHeader() {
        return header;
    }
}
