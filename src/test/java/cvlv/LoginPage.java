package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv login page.
 */
public class LoginPage {
    BaseFunctions baseFunctions;
    private static final By FORGOT_PASSWORD_LINK = By.xpath("//a[@href='/password']");
    private static final By LOGIN_FIELD = By.id("kasutajanimi");
    private static final By PASSWORD_FIELD = By.id("parool");
    private static final By LOGIN_BTN = By.className("btn_green_submit");
    private static final Logger LOGGER = Logger.getLogger(LoginPage.class);

    public LoginPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Login page opened");
    }

    /**
     * Method clicks on Forgot Password link
     *
     * @return password recovery page
     */
    public ForgotPasswordPage clickForgotPasswordLink() {
        baseFunctions.waitForElement(FORGOT_PASSWORD_LINK, 1000);
        baseFunctions.click(FORGOT_PASSWORD_LINK);
        return new ForgotPasswordPage(baseFunctions);
    }

    /**
     * Method to fill login form
     *
     * @param login user login
     * @param password user password
     */
    public void fillLoginForm(String login, String password) {
        baseFunctions.waitForElement(LOGIN_FIELD, 500);
        baseFunctions.fillInput(LOGIN_FIELD, login);
        LOGGER.info("User is filling login field");
        baseFunctions.waitForElement(PASSWORD_FIELD, 500);
        baseFunctions.fillInput(PASSWORD_FIELD, password);
        LOGGER.info("User is filling password field");
    }

    /**
     * Method submits login form
     */
    public void clickLoginBtn() {
        baseFunctions.click(LOGIN_BTN);
        LOGGER.info("User clicks on submit button");
    }
}
