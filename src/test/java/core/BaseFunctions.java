package core;

import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * This class contains basic Selenium functions
 */

public class BaseFunctions {
    WebDriver driver;
    private static final String FIREFOX_DRIVER_LOCATION = "/Users/elinkin/Downloads/QA course/geckodriver";
    private static final Logger LOGGER = Logger.getLogger(BaseFunctions.class);

    public BaseFunctions() {

        /**
         * Method to start the webdriver
         */

        LOGGER.info("Setting global property for driver" + FIREFOX_DRIVER_LOCATION);
        System.setProperty("webdriver.gecko.driver", FIREFOX_DRIVER_LOCATION);

        LOGGER.info("Opening Firefox driver");
        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("security.insecure_field_warning.contextual.enabled", false);
        profile.setPreference("security.insecure_password.ui.enabled", false);
        this.driver = new FirefoxDriver(profile);

        LOGGER.info("Maximize driver window size");
        driver.manage().window().maximize();
    }

    /**
     * Method to stop the webdriver
     */
    public void stopDriver() {
        LOGGER.info("Stopping driver");
        driver.quit();
    }


    /**
     * This method goes to specific URL
     *
     * @param url web address
     */
    public void goToUrl(String url) {
        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            url = "http://" + url;
        }
        LOGGER.info("User goes to: " + url);
        driver.get(url);
    }

    /**
     * Method to click on a specific element
     *
     * @param element - element to click
     */
    public void click(By element) {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        driver.findElement(element).click();
    }

    /**
     * This method clears input field and types text
     *
     * @param element - text field to fill
     * @param text    - text to type in
     */
    public void fillInput(By element, String text) {
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(text);
    }

    /**
     * This method clears input field and types numbers only
     *
     * @param element - text field to fill
     * @param integer  - text to type in
     */
    public void fillNumberInput(By element, Integer integer) {
        driver.findElement(element).clear();
        driver.findElement(element).sendKeys(String.valueOf(integer));
    }

    /**
     * This method returns the value of the element
     *
     * @param element - element locator to search
     */
    public String getValue(By element) {
       return driver.findElement(element).getAttribute("value");
    }

    /**
     * This method created to pause test - needs to wait for data refresh or receiving mail message
     *
     * @param mills time to wait in milliseconds
     */
    public void pause(long mills) {
        try {
            Thread.sleep(mills);
            LOGGER.info("Test pauses for " + mills + " milliseconds to wait for data");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method is waiting for element to be added in DOM
     *
     * @param element - element to wait
     * @param mills   - max time to wait in milliseconds
     */
    public void waitForElement(By element, long mills) {
        WebDriverWait wait = new WebDriverWait(driver, mills);
        wait.until(ExpectedConditions.presenceOfElementLocated(element));
    }

    /**
     * Method checks if element is available in DOM
     *
     * @param element element to check
     * @return true or false
     */
    public boolean isPresentElement(By element) {
        List<WebElement> elements = driver.findElements(element);
        if (elements.size() != 0) {
            return true;
        }
        return false;
    }

    /**
     * Method returns WebElement with a specific locator
     *
     * @param element - element locator to search
     * @return WebElement
     */
    public WebElement findElement(By element) {
        return driver.findElement(element);
    }

    /**
     * Method returns a list of elements with a specific locator
     *
     * @param element - element locator to search
     * @return list of WebElements
     */
    public List<WebElement> findElements(By element) {
        return driver.findElements(element);
    }

    /**
     * Method removes focus from the field
     *
     * @param element - element locator to search
     */
    public JavascriptExecutor simulateBlurEvent(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].focus(); arguments[0].blur(); return true", element);
        return js;
    }
}