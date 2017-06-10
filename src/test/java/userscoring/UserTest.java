package userscoring;

import core.BaseFunctions;
import org.apache.commons.lang3.RandomStringUtils;
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
    private static final By NAME_ERROR_TXT = By.xpath("//div[contains(text(), 'Name is not valid')]");
    private static final By PHONE_ERROR_TXT = By.xpath("//div[contains(text(), 'Phone is not valid')]");
    private static final By EMAIL_ERROR_TXT = By.xpath("//div[contains(text(), 'Email is not valid')]");
    private static final By ID_ERROR_TXT = By.xpath("//div[contains(text(), 'ID is not valid')]");
    private static final By AGE_ERROR_TXT = By.xpath("//div[contains(text(), 'Age is not valid')]");
    private static final By CHILDREN_ERROR_TXT = By.xpath("//div[contains(text(), 'Number of children is not valid')]");
    private static final By CITY_ERROR_TXT = By.xpath("//div[contains(text(), 'City is not valid')]");
    private static final By COUNTRY_ERROR_TXT = By.xpath("//div[contains(text(), 'Country is not valid')]");
    private static final By GENDER_COMBO_BOX = By.xpath("//select[@name='gender']");
    private static final By GENDER = By.xpath("(//div[@class='gender'])[last()]");
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
            Assert.assertTrue("Wrong score", testData.expectedScore == score);
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
    public void emptyUserFieldsTest() {
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
    public void userWhitespaceTest() throws InterruptedException {
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

        Thread.sleep(1000);

        Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(NAME));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void userAllowedCharactersTest() throws InterruptedException {
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

        Thread.sleep(1000);

        Assert.assertTrue("No validation for name/surname fields", baseFunctions.isPresentElement(NAME));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(NAME_ERROR_TXT));
    }

    @Test
    public void phoneValidationTest() throws InterruptedException {
        String PHONETEXT = "1";
        By PHONE = By.xpath("//input[@name='phone']");

        LOGGER.info("Verifying that it is not possible to create a user if phone number field contains less than 5 digits or more than 11 digits and a plus");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(PHONE, 500);

        addClientPage.addUser(NAME, SURNAME, PHONETEXT, EMAIL, ID);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for phone field", baseFunctions.isPresentElement(PHONE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(PHONE_ERROR_TXT));
    }

    @Test
    public void emailValidationTest() throws InterruptedException {
        String EMAILTEXT = "test";
        By EMAIL = By.xpath("//input[@name='email']");

        LOGGER.info("Verifying that e-mail has a valid format)");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(EMAIL, 500);

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAILTEXT, ID);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for email field", baseFunctions.isPresentElement(EMAIL));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMAIL_ERROR_TXT));
    }

    @Test
    public void genderValidationTest() throws InterruptedException {
        LOGGER.info("Check that gender selection drop-down works (it is possible to select different options)");

        baseFunctions.goToUrl(CLIENT_LIST_URL);

        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(GENDER_COMBO_BOX, 500);
        baseFunctions.selectComboBoxByValue(GENDER_COMBO_BOX,"female");
        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertEquals("Incorrect gender", "female", baseFunctions.getText(GENDER));

    }

    @Test
    public void idValidationTest() throws InterruptedException {
        String IDTEXT = "1";
        By ID = By.xpath("//input[@name='personId']");

        LOGGER.info("Verifying that it is not possible to create a user with incorrect personal ID format (anything other than xxxxxx-xxxxx)");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(ID, 500);

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, IDTEXT);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for ID field", baseFunctions.isPresentElement(ID));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(ID_ERROR_TXT));
    }

    @Test
    public void nameLengthTest() throws InterruptedException {
        String NAMETEXT = RandomStringUtils.randomAlphabetic(65);
        String SURNAMETEXT = RandomStringUtils.randomAlphabetic(65);
        By NAME = By.xpath("//input[@name='name']");
        By SURNAME = By.xpath("//input[@name='surname']");

        LOGGER.info("Verifying that it is not possible to enter more than 64 symbols in name/surname fields");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(SURNAME, 500);

        addClientPage.addUser(NAMETEXT, SURNAMETEXT, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertTrue("No length validation for name/surname field", baseFunctions.isPresentElement(NAME));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(NAME_ERROR_TXT));
    }

    @Test
    public void emailLengthTest() throws InterruptedException {
        String EMAILTEXT = RandomStringUtils.randomAlphabetic(257);
        By EMAIL = By.xpath("//input[@name='email']");

        LOGGER.info("Check that it is not possible to enter more than 256 symbols in e-mail field");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        baseFunctions.waitForElement(EMAIL, 500);

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAILTEXT, ID);
        addClientPage.clickAddUserButton();

        Thread.sleep(1000);

        Assert.assertTrue("No length validation for email field", baseFunctions.isPresentElement(EMAIL));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMAIL_ERROR_TXT));
    }

    @Test
    public void emptyScoreFormTest() {
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");

        LOGGER.info("Verifying that Add User form fields are initially empty");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        Assert.assertTrue("Age field not empty", baseFunctions.getValue(AGE).isEmpty());
        Assert.assertTrue("City field not empty", baseFunctions.getValue(CITY).isEmpty());
        Assert.assertTrue("Country field not empty", baseFunctions.getValue(COUNTRY).isEmpty());
        Assert.assertTrue("Children field not empty", baseFunctions.getValue(CHILDREN).isEmpty());
    }

    @Test
    public void emptyScoreFieldsTest() throws InterruptedException {
        By AGE = By.xpath("//input[@name='age']");

        LOGGER.info("Verifying that score is not calculated if one or more fields are empty");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(AGE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void scoreWhitespaceTest() throws InterruptedException {
        String AGETEXT = " ";
        String CITYTEXT = " ";
        String COUNTRYTEXT = " ";
        String CHILDRENTEXT = " ";
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");

        LOGGER.info("Verifying that score is not calculated if one or more fields are filled with white space");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);
        baseFunctions.fillInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for empty fields", baseFunctions.isPresentElement(AGE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(EMPTY_ERROR_TXT));
    }

    @Test
    public void scoreTooYoungTest() throws InterruptedException {
        int AGETEXT = 17;
        String CITYTEXT = "Riga";
        String COUNTRYTEXT = "Latvia";
        int CHILDRENTEXT = 0;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that user is not created if age is <18");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for age field", baseFunctions.isPresentElement(AGE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(AGE_ERROR_TXT));
    }

    @Test
    public void scoreTooOldTest() throws InterruptedException {
        int AGETEXT = 76;
        String CITYTEXT = "Riga";
        String COUNTRYTEXT = "Latvia";
        int CHILDRENTEXT = 0;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that user is not created if age is >75");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for age field", baseFunctions.isPresentElement(AGE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(AGE_ERROR_TXT));
    }

    @Test
    public void ageValidationTest() throws InterruptedException {
        String AGETEXT = "test";
        String CITYTEXT = "Riga";
        String COUNTRYTEXT = "Latvia";
        int CHILDRENTEXT = 0;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that it is not possible to add a score if age is not a number");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for age field", baseFunctions.isPresentElement(AGE));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(AGE_ERROR_TXT));
    }

    @Test
    public void childrenValidationTest() throws InterruptedException {
        int AGETEXT = 18;
        String CITYTEXT = "Riga";
        String COUNTRYTEXT = "Latvia";
        String CHILDRENTEXT = "test";
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that it is not possible to add a score if number of children is not a number");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for children field", baseFunctions.isPresentElement(CHILDREN));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(CHILDREN_ERROR_TXT));
    }

    @Test
    public void childrenNegativeValidationTest() throws InterruptedException {
        int AGETEXT = 18;
        String CITYTEXT = "Riga";
        String COUNTRYTEXT = "Latvia";
        int CHILDRENTEXT = -1;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that it is not possible to add a score if city field contains numbers/special characters");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for children field", baseFunctions.isPresentElement(CHILDREN));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(CHILDREN_ERROR_TXT));
    }

    @Test
    public void cityValidationTest() throws InterruptedException {
        int AGETEXT = 18;
        int CITYTEXT = 18;
        String COUNTRYTEXT = "Latvia";
        int CHILDRENTEXT = 0;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that it is not possible to add a score if country field contains numbers/special characters");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillNumberInput(CITY, CITYTEXT);
        baseFunctions.fillInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for city field", baseFunctions.isPresentElement(CHILDREN));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(CITY_ERROR_TXT));
    }

    @Test
    public void countryValidationTest() throws InterruptedException {
        int AGETEXT = 18;
        String CITYTEXT = "Riga";
        int COUNTRYTEXT = 18;
        int CHILDRENTEXT = 0;
        By AGE = By.xpath("//input[@name='age']");
        By CITY = By.xpath("//input[@name='city']");
        By COUNTRY = By.xpath("//input[@name='country']");
        By CHILDREN = By.xpath("//input[@name='childCount']");


        LOGGER.info("Verifying that it is not possible to add a score if number of children is not a number");

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        baseFunctions.waitForElement(CHILDREN, 500);

        baseFunctions.fillNumberInput(AGE, AGETEXT);
        baseFunctions.fillInput(CITY, CITYTEXT);
        baseFunctions.fillNumberInput(COUNTRY, COUNTRYTEXT);
        baseFunctions.fillNumberInput(CHILDREN, CHILDRENTEXT);
        addScorePage.clickAddScoreButton();

        Thread.sleep(1000);

        Assert.assertTrue("No validation for country field", baseFunctions.isPresentElement(COUNTRY));
        Assert.assertTrue("No error message", baseFunctions.isPresentElement(COUNTRY_ERROR_TXT));
    }

    /**
     * Stopping webDriver
     */
    @AfterClass
    public static void closeDriver() {
        baseFunctions.stopDriver();
    }
}
