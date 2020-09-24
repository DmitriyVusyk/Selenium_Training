import listeners.EventListener;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

public class BaseTest {
    protected static EventFiringWebDriver driver;

    @BeforeAll
    static void setUp() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");

/*      LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        options.setCapability(CapabilityType.LOGGING_PREFS, logPrefs);
        options.setExperimentalOption("w3c", false);
        */

        driver = new EventFiringWebDriver(new ChromeDriver(options));
        driver.register(new EventListener());
        driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);

       // driver = new ChromeDriver(options);

    }

    @AfterAll
    static void afterAll() {
        if (driver != null) {
            driver.quit();
        }
    }

}
