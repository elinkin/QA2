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
    private static final String NAME = "Test";
    private static final String SURNAME = "Testt";
    private static final String PHONE = "+37111111111";
    private static final String EMAIL = "email";
    private static final String ID = "111111-11111";
    private static final String CLIENT_LIST_URL = "qaguru.lv:8080/qa2/";
    private static final By EMPTY_ERROR_TXT = By.xpath("//div[contains(text(), 'Some fields are empty')]");
    private static final By EMAIL_ERROR_TXT = By.xpath("//div[contains(text(), 'Email is not valid')]");
    private static BaseFunctions baseFunctions = new BaseFunctions();

    @Test
    public void userScoreTest() throws IOException {
        LOGGER.info("Verifying that user scores are calculated correctly");
        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);

        TestBase testBase = new TestBase();
        for (TestBase.TestData testData : testBase.testData) {
            LOGGER.info("Creating users and adding user scores");
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
    public void emptyFormTest() {
        By NAME = By.xpath("//input[@name='name']");
        By SURNAME = By.xpath("//input[@name='surname']");
        By PHONE = By.xpath("//input[@name='phone']");
        By EMAIL = By.xpath("//input[@name='email']");
        By ID = By.xpath("//input[@name='personId']");

        LOGGER.info("Verifying that Add User form fields are initially empty");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(ID, 500);

        Assert.assertTrue("Name field not empty", baseFunctions.getValue(NAME).isEmpty());
        Assert.assertTrue("Surname field not empty", baseFunctions.getValue(SURNAME).isEmpty());
        Assert.assertTrue("Phone field not empty", baseFunctions.getValue(PHONE).isEmpty());
        Assert.assertTrue("Email field not empty", baseFunctions.getValue(EMAIL).isEmpty());
        Assert.assertTrue("ID field not empty", baseFunctions.getValue(ID).isEmpty());
    }

    @Test
    public void emptyFieldsTest() {
        By NAME = By.xpath("//input[@name='name']");

        LOGGER.info("Verifying that user is not created if one or more fields are empty");
        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();
        addClientPage.clickAddUserButton();

        baseFunctions.waitForElement(EMPTY_ERROR_TXT, 500);

        Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(NAME));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void whitespaceTest() {
        String NAMETEXT = " ";
        String SURNAMETEXT = " ";
        String PHONETEXT = " ";
        String EMAILTEXT = " ";
        String IDTEXT = " ";
        By NAME = By.xpath("//input[@name='name']");
        By SURNAME = By.xpath("//input[@name='surname']");
        By PHONE = By.xpath("//input[@name='phone']");
        By EMAIL = By.xpath("//input[@name='email']");
        By ID = By.xpath("//input[@name='personId']");


        LOGGER.info("Verifying that user is not created if one or more fields are filled with white space");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(ID, 500);
        baseFunctions.fillInput(NAME, NAMETEXT);
        baseFunctions.fillInput(SURNAME, SURNAMETEXT);
        baseFunctions.fillInput(PHONE, PHONETEXT);
        baseFunctions.fillInput(EMAIL, EMAILTEXT);
        baseFunctions.fillInput(ID, IDTEXT);
        addClientPage.clickAddUserButton();

        //baseFunctions.waitForElement(ERROR_TXT, 500);

        Assert.assertEquals(true, baseFunctions.findElement(NAME).isDisplayed());
        //Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(NAME));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void allowedCharactersTest() {
        String NAMETEXT = "111";
        String SURNAMETEXT = "@@@";
        By NAME = By.xpath("//input[@name='name']");
        By SURNAME = By.xpath("//input[@name='surname']");

        LOGGER.info("Verifying that it is not possible to create a user if name/surname contain numbers/special characters");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(SURNAME, 500);

        addClientPage.addUser(NAMETEXT, SURNAMETEXT, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        //baseFunctions.waitForElement(ERROR_TXT, 500);

        Assert.assertEquals(true, baseFunctions.findElement(NAME).isDisplayed());
        //Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(NAME));
        //Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void phoneTest() {
        LOGGER.info("Check that it is not possible to create a user if phone number field contains less than 5 digits or more than 11 digits and a plus");
        LOGGER.info("Check that it is not possible to create a user with incorrect personal ID format (anything other than xxxxxx-xxxxx)");
        LOGGER.info("Check that it is not possible to enter more than 64 symbols in name/surname fields");
        LOGGER.info("Check that it is not possible to enter more than 256 symbols in e-mail field");
        LOGGER.info("Check that gender selection drop-down works (it is possible to select different options)");
        LOGGER.info("Check that e-mail has a valid format");

        LOGGER.info("Check that Add score form fields are initially empty");
        LOGGER.info("Check that score cannot be added if one or more fields are empty");
        LOGGER.info("Check for user is not created if one or more fields contain breakspace, check for error message");
        LOGGER.info("Check that user is not created if age is <18 or >75");
        LOGGER.info("Check that it is not possible to add a score if age/number of children is not a number");
        LOGGER.info("Check that it is not possible to add a score if number of children is <0");
        LOGGER.info("Check that it is not possible to add a score if city/country fields contain numbers/special characters");
    }

    /**
     * Stopping webDriver
     */
    @AfterClass
    public static void closeDriver() {
        baseFunctions.stopDriver();
    }
}
