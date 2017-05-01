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
        baseFunctions.waitForElement(FORGOT_PASSWORD_LINK, 500);
        baseFunctions.click(FORGOT_PASSWORD_LINK);
        return new ForgotPasswordPage(baseFunctions);
    }
}
