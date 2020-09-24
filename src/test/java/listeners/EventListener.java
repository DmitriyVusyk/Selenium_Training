package listeners;

import com.esotericsoftware.minlog.Log;
import com.google.common.io.Files;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.events.AbstractWebDriverEventListener;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import static java.lang.Thread.sleep;

public class EventListener extends AbstractWebDriverEventListener {

    private static final Logger LOG = LogManager.getLogger(EventListener.class);

    @Override
    public void beforeFindBy(By by, WebElement element, WebDriver driver) {
        highlight(element, "red", driver);
        LOG.info("Find element: " + by);
    }

    @Override
    public void afterFindBy(By by, WebElement element, WebDriver driver) {
        highlight(element, "green", driver);
        LOG.info("Element found");
    }

    @Override
    public void beforeClickOn(WebElement element, WebDriver driver) {
        highlight(element, "yellow",driver);
        LOG.info("Should click " + element.getTagName());
    }

    @Override
    public void afterClickOn(WebElement element, WebDriver driver) {
        LOG.info("Clicked successfully");
    }

    @Override
    public void beforeChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        highlight(element, "blue", driver);
        LOG.info("Send: " + Arrays.toString(keysToSend) + " to: " + element.getTagName());
    }

    @Override
    public void afterChangeValueOf(WebElement element, WebDriver driver, CharSequence[] keysToSend) {
        LOG.info("Changed successfully");
    }

    @Override
    public void beforeSwitchToWindow(String windowName, WebDriver driver) {
        LOG.info("Switching to: " + windowName);
    }

    @Override
    public void afterSwitchToWindow(String windowName, WebDriver driver) {
        LOG.info("Switched");
    }

    @Override
    public void onException(Throwable throwable, WebDriver driver) {

        Log.info("[Shit Happened:] " + throwable.getMessage().split(":")[0]);

        File tempFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.copy(tempFile,
                    new File(System.currentTimeMillis() + "screen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("[Screenshot captured]");

    }


    @Override
    public void beforeGetText(WebElement element, WebDriver driver) {
        LOG.info("Getting text from: " + element.getTagName());
    }

    @Override
    public void afterGetText(WebElement element, WebDriver driver, String text) {
        LOG.info("Text gotten" );
    }


    
    public static <T extends WebElement> T highlight(T element, final String color, WebDriver driver) {
        if (element != null && element.getAttribute("__selenideHighlighting") == null) {
            for (int i = 1; i < 4; i++) {
                transform(element, color, i, driver);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 4; i > 0; i--) {
                transform(element, color, i, driver);
                try {
                    sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        return element;
    }

    private static void transform(WebElement element, String color, int i, WebDriver driver) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].setAttribute('__selenideHighlighting', 'done'); " +
                        "arguments[0].setAttribute(arguments[1], arguments[2])",
                element,
                "style",
                "border: " + i + "px solid " + color + "; border-style: dotted; " +
                        "transform: scale(1, 1." + i + ");");
    }
}