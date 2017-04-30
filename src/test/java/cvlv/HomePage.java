package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv home page class.
 */
public class HomePage {
    BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(HomePage.class);
    private static final By LOGIN_BTN = By.xpath("//a[@href='/login']");
    private static final By EXIT_BTN = By.id("topnav_logout");

    public HomePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Home page is loaded");
    }
    /**
     * Method to open Sign In page
     *
     * @return sign In page
     */
    public LoginPage clickLoginButton() {
        baseFunctions.click(LOGIN_BTN);
        LOGGER.info("User clicks Login button");
        return new LoginPage(baseFunctions);
    }

    /**
     * Method indicates if Exit button presents on page
     * this indicates if user is logged in
     *
     * @return true or false
     */
    public boolean isPresentAccountBtn() {
        LOGGER.info("Checking if Account button presents on page");
        baseFunctions.waitForElement(EXIT_BTN, 500);
        return baseFunctions.isPresentElement(EXIT_BTN);
    }
}
