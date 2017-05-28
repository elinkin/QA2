package userscoring;

import core.BaseFunctions;
import org.openqa.selenium.By;
import org.apache.log4j.Logger;

public class AddClientPage {
    BaseFunctions baseFunctions;
    private static final By ADD_USER_BUTTON = By.xpath("//input[@type='submit']");
    private static final By NAME = By.xpath("//input[@name='name']");
    private static final By SURNAME = By.xpath("//input[@name='surname']");
    private static final By PHONE = By.xpath("//input[@name='phone']");
    private static final By EMAIL = By.xpath("//input[@name='email']");
    private static final By ID = By.xpath("//input[@name='personId']");
    private static final Logger LOGGER = Logger.getLogger(AddClientPage.class);

    public AddClientPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Add Client page opened");
    }

    /**
     * Method to fill login form
     *
     * @param name - user name
     * @param surname - user surname
     * @param email - user email
     * @param personId - user's personal ID
     */
    public void addUser(String name, String surname, String phone, String email, String personId) {
        LOGGER.info("User is filling in name field");
        baseFunctions.waitForElement(NAME, 500);
        baseFunctions.fillInput(NAME, name);
        LOGGER.info("User is filling in surname field");
        baseFunctions.waitForElement(SURNAME, 500);
        baseFunctions.fillInput(SURNAME, surname);
        LOGGER.info("User is filling in phone field");
        baseFunctions.waitForElement(PHONE, 500);
        baseFunctions.fillInput(PHONE, phone);
        LOGGER.info("User is filling in email field");
        baseFunctions.waitForElement(EMAIL, 500);
        baseFunctions.fillInput(EMAIL, email);
        LOGGER.info("User is filling in personal ID field");
        baseFunctions.waitForElement(ID, 500);
        baseFunctions.fillInput(ID, personId);
        LOGGER.info("User is filling login field");
    }

    /**
     * Method clicks on Add User button
     */
    public void clickAddUserButton() {
        baseFunctions.click(ADD_USER_BUTTON);
        LOGGER.info("User clicked on Add User button");
    }
}