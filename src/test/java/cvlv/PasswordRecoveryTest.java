package cvlv;

import com.sun.xml.internal.messaging.saaj.packaging.mime.MessagingException;
import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;

/**
 * /**
 * This test restores password via E-mail using gmail and checks if password is restored
 */
public class PasswordRecoveryTest {

    private static final String LOGIN = "elinkinspam@gmail.com";
    private static final String PASSWORD = "Elinka1980";
    private static final String WORKIN_DIRECTORY = "INBOX";
    private static final String WEB_SITE_URL = "cv.lv";
    private static final String NEW_PASSWORD = "passw0rdTest";
    private static final Logger LOGGER = Logger.getLogger(PasswordRecoveryTest.class);
    private BaseFunctions baseFunctions = new BaseFunctions();

    /**
     * method creates mail session
     *
     * @throws MessagingException
     */

    @Test
    public void restorePassword() throws MessagingException, javax.mail.MessagingException, IOException, InterruptedException {
        LOGGER.info("This test restores password via E-mail using gmail and checks if password is restored");
        baseFunctions.goToUrl(WEB_SITE_URL);
        HomePage homePage = new HomePage(baseFunctions);

        LOGGER.info("User goes to Login page");
        LoginPage loginPage = homePage.clickLoginButton();

        LOGGER.info("User clicks on Forgot Password link");
        ForgotPasswordPage forgotPasswordPage = loginPage.clickForgotPasswordLink();

        LOGGER.info("Filling E-mail fields with: " + LOGIN + " and submitting form");
        forgotPasswordPage.fillEmailField(LOGIN);
        forgotPasswordPage.clickSubmitBtn();

        LOGGER.info("Checking mail for recovery link");
        MailHelper mailHelper = new MailHelper(LOGIN, PASSWORD);

        LOGGER.info("User goes to received URL");
        baseFunctions.goToUrl(mailHelper.getMail(WORKIN_DIRECTORY));
        CreateNewPasswordPage createNewPasswordPage = new CreateNewPasswordPage(baseFunctions);

        LOGGER.info("Filling fields with a new password " + NEW_PASSWORD + " and submitting the form");
        createNewPasswordPage.fillNewPassword(NEW_PASSWORD);
        ChangePasswordEndPage changePasswordEndPage = createNewPasswordPage.clickSubmitBtn();

        Assert.assertTrue("No Success text block", changePasswordEndPage.isPresentSuccessTextBlock());
        LOGGER.info("Password changed successfully, logging off");
        changePasswordEndPage.clickLoginButton();

        LOGGER.info("User tries to login with a new password");
        homePage = login(LOGIN, NEW_PASSWORD);

        Assert.assertTrue("Not logged in", homePage.isPresentAccountBtn());
        LOGGER.info("Login with a new password is successful");
    }

    /**
     * Stopping webDriver
     */
    @After
    public void closeDriver() {
        baseFunctions.stopDriver();
    }

    /**
     * This method is signing in the user to cv.lv
     *
     * @param login - user login
     * @param password - user password
     * @return - home page
     */
    private HomePage login(String login, String password) {
        baseFunctions.goToUrl(WEB_SITE_URL);
        HomePage homePage = new HomePage(baseFunctions);
        homePage.clickLoginButton();
        LoginPage loginPage = new LoginPage(baseFunctions);
        loginPage.fillLoginForm(login, password);
        loginPage.clickLoginBtn();
        return new HomePage(baseFunctions);
    }
}
