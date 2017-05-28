package userscoring;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class AddScorePage {
    BaseFunctions baseFunctions;
    private static final By ADD_SCORE_BUTTON = By.xpath("//input[@type='submit']");
    private static final By AGE = By.xpath("//input[@name='age']");
    private static final By CITY = By.xpath("//input[@name='city']");
    private static final By COUNTRY = By.xpath("//input[@name='country']");
    private static final By CHILDREN = By.xpath("//input[@name='childCount']");
    private static final Logger LOGGER = Logger.getLogger(AddClientPage.class);

    public AddScorePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Add Client page opened");
    }

    /**
     * Method to fill login form
     *
     * @param age - user name
     * @param city - user surname
     * @param country - user email
     * @param childCount - user's personal ID
     */
    public void addScore(Integer age, String city, String country, Integer childCount) {
        LOGGER.info("User is filling in age field");
        baseFunctions.waitForElement(AGE, 500);
        baseFunctions.fillNumberInput(AGE, age);
        LOGGER.info("User is filling in city field");
        baseFunctions.waitForElement(CITY, 500);
        baseFunctions.fillInput(CITY, city);
        LOGGER.info("User is filling in country field");
        baseFunctions.waitForElement(COUNTRY, 500);
        baseFunctions.fillInput(COUNTRY, country);
        LOGGER.info("User is filling in children field");
        baseFunctions.waitForElement(CHILDREN, 500);
        baseFunctions.fillNumberInput(CHILDREN, childCount);
        LOGGER.info("User is filling login field");
    }

    /**
     * Method clicks on Add Score button
     */
    public void clickAddScoreButton() {
        baseFunctions.click(ADD_SCORE_BUTTON);
        LOGGER.info("User clicked on Add User button");
    }
}
