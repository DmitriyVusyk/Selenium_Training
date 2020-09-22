import org.junit.Assert;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static constants.Constants.*;

public class LiteCartHW4 extends BaseTest {
    private static final String INPUT_FIELD_USERNAME_XPATH = "//*[@class='form-control' and @name='username']";
    private static final String INPUT_FIELD_PASSWORD_XPATH = "//*[@class='form-control' and @name='password']";
    private static final String BTN_LOGIN_XPATH = "//*[@class='btn btn-default'][@value='Login']";
    private static final String SIDEBAR_CSS_SELECTOR = "#box-apps-menu";
    public static final String WIKI_LINK = "en.wikipedia.org/wiki/";

    private WebDriverWait wait = new WebDriverWait(driver, 10);

    @Tag("Verify-that-every-section-on-admin-page-has-header")
    @Test
    @Order(1)
    void linksAreOpenedInNewWindow() {
        driver.get(LITE_CART_LOGIN_FORM_URL);
        login(USERNAME, PASSWORD);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//span[@class='name' and text()='Countries']/parent::a"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[text()='Spain']"))).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='content']")));
        List<WebElement> links = driver.findElements(By.xpath("//a[contains(@href, '" + WIKI_LINK + "')]"));
        links.forEach(link -> {
            String originalWindow = driver.getWindowHandle();
            String expectedURL = link.getAttribute("href");
            expectedURL = removeProtocol(expectedURL);
            link.click();
            waitForNewWindow(driver, 10);
            Set<String> openedWindows = driver.getWindowHandles();
            openedWindows.remove(originalWindow);
            ArrayList<String> mainList = new ArrayList<>(openedWindows);
            driver.switchTo().window(mainList.get(0));
            driver.findElement(By.cssSelector("#mw-panel"));
            String actualURL = driver.getCurrentUrl();
            actualURL = removeProtocol(actualURL);
            Assert.assertEquals("Url are equals: ", expectedURL, actualURL);
            driver.close();
            driver.switchTo().window(originalWindow);
        });
    }

    private void login(String username, String password) {
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

    private boolean waitForNewWindow(WebDriver driver, int timeoutInSeconds) {
        boolean flag = false;
        int counter = 0;
        while (!flag) {
            try {
                Set<String> winId = driver.getWindowHandles();
                if (winId.size() > 1) {
                    flag = true;
                    return flag;
                }
                Thread.sleep(1000);
                counter++;
                if (counter > timeoutInSeconds) {
                    return flag;
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                return false;
            }
        }
        return flag;
    }

    private String removeProtocol(String url) {
        String result = "";
        try {
            URL originUrl = new URL(url);
            String protocol = originUrl.getProtocol();
            result = url.replaceFirst(protocol + ":", "");
            if (result.startsWith("//")) {
                result = result.substring(2);
                return result;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}