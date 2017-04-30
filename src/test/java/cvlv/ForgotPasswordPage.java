package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv password recovery page.
 */
public class ForgotPasswordPage {
    BaseFunctions baseFunctions;
    private static final By RECOVERY_EMAIL_FIELD = By.id("email");
    private static final By SUBMIT_BTN = By.className("blue_submit");
    private static final Logger LOGGER = Logger.getLogger(ForgotPasswordPage.class);

    public ForgotPasswordPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Password recovery page is opened");
    }

    /**
     * Method fills text input with e-mail account to restore
     *
     * @param email email address
     */
    public void fillEmailField(String email) {
        baseFunctions.waitForElement(RECOVERY_EMAIL_FIELD, 500);
        baseFunctions.fillInput(RECOVERY_EMAIL_FIELD, email);
        LOGGER.info("User types in the E-mail field");
    }

    /**
     * Method click on submit button
     */
    public void clickSubmitBtn() {
        baseFunctions.click(SUBMIT_BTN);
        baseFunctions.pause(3000);
        LOGGER.info("User clicks on submit button");
    }

}
