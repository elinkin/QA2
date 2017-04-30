package cvlv;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * cv.lv success password recovery page.
 */
public class ChangePasswordEndPage {

        BaseFunctions baseFunctions;
        private static final By SUCCESS_TXT = By.className("box-inner inner-content");
        private static final By SIGNIN_BTN = By.className("blue_submit");
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
            baseFunctions.waitForElement(SUCCESS_TXT, 10000);
            return baseFunctions.isPresentElement(SUCCESS_TXT);
        }

        /**
         * Method clicks on Login button
         */
        public void clickLoginButton() {
            baseFunctions.click(SIGNIN_BTN);
            LOGGER.info("User clicked on Login button");
        }
    }
