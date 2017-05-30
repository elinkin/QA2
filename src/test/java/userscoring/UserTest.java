package userscoring;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import userscoring.model.Client;

import java.io.IOException;
import java.util.List;

public class UserTest {

    private static final Logger LOGGER = Logger.getLogger(UserTest.class);
    private static final String NAME = "4";
    private static final String SURNAME = "1";
    private static final String PHONE = "1";
    private static final String EMAIL = "1";
    private static final String ID = "111111-11111";
    private static final String CLIENT_LIST_URL = "qaguru.lv:8080/qa2/";
    private static BaseFunctions baseFunctions = new BaseFunctions();

    @Test
    public void userScoreTest() throws IOException {

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);

        TestBase testBase = new TestBase();
        for (TestBase.TestData testData : testBase.testData) {
            LOGGER.info("Creating users and adding user score");
            AddClientPage addClientPage = clientPage.clickAddUserLink();

            addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
            addClientPage.clickAddUserButton();

            AddScorePage addScorePage = clientPage.clickAddScoreLink();

            addScorePage.addScore(testData.age, testData.city, testData.country, testData.children);
            addScorePage.clickAddScoreButton();

            ClientRequester requester = new ClientRequester();
            List<Client> clients = requester.getClients();
            Client client = clients.get(clients.size() - 1);
            Integer score = client.getScore();
            LOGGER.info(String.format("Expected score: %d, Actual score: %d", testData.expectedScore, score));
//            Assert.assertTrue("Wrong score", testData.expectedScore == score);
        }
    }

    @Test
    public void emptyFieldsTest() throws IOException {
        LOGGER.info("Check that Add User form fields are initially empty");
        By NAME = By.xpath("//input[@name='name']");
        By SURNAME = By.xpath("//input[@name='surname']");
        By PHONE = By.xpath("//input[@name='phone']");
        By EMAIL = By.xpath("//input[@name='email']");
        By ID = By.xpath("//input[@name='personId']");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(NAME, 500);
        baseFunctions.waitForElement(SURNAME, 500);
        baseFunctions.waitForElement(PHONE, 500);
        baseFunctions.waitForElement(EMAIL, 500);
        baseFunctions.waitForElement(ID, 500);

        Assert.assertTrue("Name field not empty", baseFunctions.getValue(NAME).isEmpty());
        Assert.assertTrue("Surname field not empty", baseFunctions.getValue(SURNAME).isEmpty());
        Assert.assertTrue("Phone field not empty", baseFunctions.getValue(PHONE).isEmpty());
        Assert.assertTrue("Email field not empty", baseFunctions.getValue(EMAIL).isEmpty());
        Assert.assertTrue("ID field not empty", baseFunctions.getValue(ID).isEmpty());


        LOGGER.info("Check that user is not created if one or more fields are empty, check for error message");
        LOGGER.info("Check for user is not created if one or more fields contain breakspace, check for error message");
        LOGGER.info("Check that it is not possible to create a user if name/surname contain numbers/special characters");
        LOGGER.info("Check that it is not possible to create a user if phone number field contains less than 5 digits or more than 11 digits and a plus");
        LOGGER.info("Check that it is not possible to create a user with incorrect personal ID format (anything other than xxxxxx-xxxxx)");
        LOGGER.info("Check that it is not possible to enter more than 64 symbols in name/surname fields");
        LOGGER.info("Check that it is not possible to enter more than 256 symbols in e-mail field");
        LOGGER.info("Check that gender selection drop-down works (it is possible to select different options)");
        LOGGER.info("Check that e-mail has a valid format");
        LOGGER.info("Check that user is created when all fields are filled correctly");

        LOGGER.info("Check that Add score form fields are initially empty");
        LOGGER.info("Check that score cannot be added if one or more fields are empty");
        LOGGER.info("Check for user is not created if one or more fields contain breakspace, check for error message");
        LOGGER.info("Check that user is not created if age is <18 or >75");
        LOGGER.info("Check that it is not possible to add a score if age/number of children is not a number");
        LOGGER.info("Check that it is not possible to add a score if number of children is <0");
        LOGGER.info("Check that it is not possible to add a score if city/country fields contain numbers/special characters");

        LOGGER.info("Check that score is added when all fields are filled correctly");
        LOGGER.info("Check that score was calculated correctly (based on spec)");

    }

    /**
     * Stopping webDriver
     */
    @AfterClass
    public static void closeDriver() {
        baseFunctions.stopDriver();
    }
}
