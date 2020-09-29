package pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ProductPage extends BasePage {

    @FindBy(name = "add_cart_product")
    private WebElement addToCartButton;

    @FindBy(css = ".badge.quantity")
    private WebElement cartButton;

    public ProductPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    public WebElement getAddToCartButton() {
        return addToCartButton;
    }

    public WebElement getCartButton() {
        return cartButton;
    }

    @Override
    void open() {

    }
}
