import org.junit.Assert;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static constants.Constants.GOOGLE_URL;

public class HomeWorkOneTestClass extends BaseTest {

    @Test
    void test() {
        driver.get(GOOGLE_URL);
    }
}
