package userscoring;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

public class ClientPage {
    BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(ClientPage.class);
    private static final By ADD_USER = By.xpath("//a[@href='addUser']");
    private static final By ADD_SCORE = By.xpath("(//a[contains(text(),'add score')])[last()]");
    private static final By SCORE = By.xpath("(//div[@class='score'])[last()]");

    public ClientPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Client list is loaded");
    }

    /**
     * Method to open Add Client page
     *
     * @return Add Client page
     */
    public AddClientPage clickAddUserLink() {
        baseFunctions.click(ADD_USER);
        LOGGER.info("User clicks Add User button");
        return new AddClientPage(baseFunctions);
    }

    /**
     * Method to open Add Score page
     *
     * @return Add Score page
     */
    public AddScorePage clickAddScoreLink() {
        baseFunctions.waitForElement(SCORE, 500);
        baseFunctions.click(SCORE);
        LOGGER.info("User clicks Add Score button");
        return new AddScorePage(baseFunctions);
    }

    public String loadScore() {
        baseFunctions.waitForElement(SCORE, 500);
        return baseFunctions.getValue(SCORE);
    }
}
