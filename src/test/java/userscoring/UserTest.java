package userscoring;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;
import userscoring.model.Client;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public class UserTest {

    private static final Logger LOGGER = Logger.getLogger(UserTest.class);
    private static final String NAME = "1";
    private static final String SURNAME = "1";
    private static final String PHONE = "1";
    private static final String EMAIL = "1";
    private static final String ID = "111111-11111";
    private static final Integer AGE = 18;
    private static final String CITY = "Riga";
    private static final String COUNTRY = "Latvia";
    private static final Integer CHILDREN = 0;
    private static final String CLIENT_LIST_URL = "qaguru.lv:8080/qa2/";
    private static BaseFunctions baseFunctions = new BaseFunctions();

    @Test
    public void testArray() {
        TestBase testBase = new TestBase();
        testBase.toTest();
    }


    @Test
    public void userScoreTest() throws IOException {

        baseFunctions.goToUrl(CLIENT_LIST_URL);
        ClientPage clientPage = new ClientPage(baseFunctions);

        TestBase testBase = new TestBase();
        testBase.toTest();
        //for (TestBase.TestData rec : ) {

        LOGGER.info("User goes to Add Client page");
        AddClientPage addClientPage = clientPage.clickAddUserLink();

        LOGGER.info("Filling in Add User form fields");
        addClientPage.addUser(NAME, SURNAME, PHONE, EMAIL, ID);
        addClientPage.clickAddUserButton();

        LOGGER.info("User goes to Add Score page");
        AddScorePage addScorePage = clientPage.clickAddScoreLink();

        LOGGER.info("Filling in Add Score form fields");
        addScorePage.addScore(AGE, CITY, COUNTRY, CHILDREN);
        addScorePage.clickAddScoreButton();

        ClientRequester requester = new ClientRequester();
        List<Client> clients = requester.getClients();
        Client client = clients.get(clients.size() - 1);
        Assert.assertEquals("Wrong score: ", BigDecimal.valueOf(700), client.getScore());

        String[][] clientdata = {{"Test1", "Test1", "+371111111", "test1@gmail.com", "111111-11111"}, {"Test2", "Test2", "+371222222", "test2@gmail.com", "222222-22222"},
                {"Test3", "Test3", "+371333333", "test3@gmail.com", "333333-33333"}, {"Test4", "Test4", "+371444444", "test4@gmail.com", "444444-44444"},
                {"Test5", "Test5", "+371555555", "test5@gmail.com", "5555555-55555"}, {"Test6", "Test6", "+371666666", "test6@gmail.com", "666666-66666"}};

        LOGGER.info("Check that Add User form fields are initially empty");
        //Assert.assertEquals("Input field must be empty", "", selenium.getValue("name=model.query"));

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
