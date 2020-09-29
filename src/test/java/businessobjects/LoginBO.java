package businessobjects;

import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import pageobjects.LoginPage;

public class LoginBO {
    private final LoginPage loginPage;

    public LoginBO(WebDriver driver) {
        this.loginPage = new LoginPage(driver);
    }

    public LoginPage login(String username, String password) {
        loginPage.open();
        Assert.assertTrue(isLoginPageOpened());
        loginPage.getUsernameInput().sendKeys(username);
        loginPage.getPasswordInput().sendKeys(password);
        loginPage.getSubmitButton().click();
        return loginPage;
    }

    public boolean isLoginPageOpened() {
        return loginPage.wait.until(ExpectedConditions.invisibilityOf(loginPage.getPasswordInput()));
    }
}
