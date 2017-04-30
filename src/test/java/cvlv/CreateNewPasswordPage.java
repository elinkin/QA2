package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv create new password page.
 */
public class CreateNewPasswordPage {

    BaseFunctions baseFunctions;
    private static final By NEW_PASSWORD_FIELD = By.id("password_945644682");
    private static final By RETYPE_PASSWORD_FIELD = By.id("password2_945644682");
    private static final By SUBMIT_BTN = By.className("blue_submit");
    private static final Logger LOGGER = Logger.getLogger(CreateNewPasswordPage.class);

    public CreateNewPasswordPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Create New Password page is opened");
    }

    /**
     * Method fills in fields with a new password
     *
     * @param password new password to enter
     */
    public void fillNewPassword(String password) throws InterruptedException {
        baseFunctions.waitForElement(NEW_PASSWORD_FIELD, 500);
        baseFunctions.fillInput(NEW_PASSWORD_FIELD, password);
        LOGGER.info("User typed new password");
        baseFunctions.waitForElement(RETYPE_PASSWORD_FIELD, 500);
        baseFunctions.fillInput(RETYPE_PASSWORD_FIELD, password);
        LOGGER.info("User confirmed new password");
        Thread.sleep(3000);
        baseFunctions.getElement(By.name("q")).click();
        Thread.sleep(3000);

    }

    /**
     * Method submits form with new password
     *
     * @return password recovery last page
     */
    public ChangePasswordEndPage clickSubmitBtn() {
        baseFunctions.click(SUBMIT_BTN);
        LOGGER.info("User clicks submit button");
        return new ChangePasswordEndPage(baseFunctions);
    }
}
