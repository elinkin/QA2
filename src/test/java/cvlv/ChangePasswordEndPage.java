package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv success password recovery page.
 */
public class ChangePasswordEndPage {

    BaseFunctions baseFunctions;
    private static final By SUCCESS_TXT = By.xpath("//h1[contains(text(), 'Jūsu parole ir uzstādīta/izmainīta')]");
    private static final By SIGNIN_BTN = By.className("btn_green_submit");
    private static final By LOGIN_FIELD = By.id("kasutajanimi");
    private static final By PASSWORD_FIELD = By.id("parool");
    private static final Logger LOGGER = Logger.getLogger(ChangePasswordEndPage.class);

    public ChangePasswordEndPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Change password success page is loaded");
    }

    /**
     * Method checks if successful recovery text appears on page
     *
     * @return true or false
     */
    public boolean isPresentSuccessTextBlock() {
        baseFunctions.waitForElement(SUCCESS_TXT, 6000);
        return baseFunctions.isPresentElement(SUCCESS_TXT);
    }

    /**
     * Method to fill login form
     *
     * @param login - user login
     * @param password - user password
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
     * Method clicks on Login button
     */
    public void clickLoginButton() {
        baseFunctions.click(SIGNIN_BTN);
        LOGGER.info("User clicked on Login button");
    }
}
