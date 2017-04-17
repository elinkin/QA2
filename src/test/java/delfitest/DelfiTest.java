package delfitest;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Testing comments on Delfi.lv.
 */

public class DelfiTest {

    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static WebDriver driver;
    private int commentCount;
    private int articleCommentCount;

    @BeforeClass
    public static void setUp() {
        driver = getDriver();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    /**
     * This test will test comment count on main page and article page.
     */

    @Test
    public void commentTesting () {
        LOGGER.info("We are starting our test");

        LOGGER.info("We are opening rus.delfi.lv home page");
        driver.get("http://rus.delfi.lv");

        LOGGER.info("Finding first article and getting its comment count");
        WebElement articleElement  = driver.findElement(By.xpath("(//h3[@class='top2012-title'])[1]"));
        WebElement linkElement = articleElement.findElement(By.xpath("//a[@class='top2012-title']"));
        String articleUrl = linkElement.getAttribute("href");
        try {
            WebElement commentsElement = articleElement.findElement(By.xpath("//a[@class='comment-count']"));
            commentCount = loadCommentCount(commentsElement);
        } catch (NumberFormatException e) {
            LOGGER.info("Article has no comments");
        }

        LOGGER.info("Opening first article");
        driver.get(articleUrl);

        LOGGER.info("Getting comment count from title on article page");
        WebElement articleCommentsElement = driver.findElement(By.xpath("//*[@class='comment-count']"));
        articleCommentCount = loadCommentCount(articleCommentsElement);

        LOGGER.info("Checking that the number of comments on home page and on article page is identical");
        Assert.assertEquals("Wrong comment count on article page", commentCount, articleCommentCount, 0);
        LOGGER.info("Comment count on article page is correct");

        LOGGER.info("Getting comment count for registered users");
        //WebElement regcomments = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='comments-listing']/div[3]/a[1]")));
        WebElement regUserComments = driver.findElement(By.xpath("//*[@id='comments-listing']/div/a[1]/span"));
        Integer regCountNumber = loadCommentCount(regUserComments);

        LOGGER.info("Getting comment count for anonymous users");
        WebElement anonUSerComments = driver.findElement(By.xpath("//*[@id='comments-listing']/div/a[2]/span"));
        Integer anonCountNumber = loadCommentCount(anonUSerComments);

        LOGGER.info("Checking total comment count");
        Integer total = regCountNumber + anonCountNumber;
        Assert.assertEquals("Wrong comment count on comment page", commentCount, total, 0);
        LOGGER.info("Comment count on comment page is correct");

        LOGGER.info("We are closing our browser");
    }

    /**
     * Returns comment count of article
     *
     * @return - comment count
     */

    private Integer loadCommentCount (WebElement element) {
        String counterString = element.getText();
        Integer numberOfComments = Integer.parseInt(counterString.substring(1, counterString.length() - 1));
        LOGGER.info("Comment count is " + numberOfComments);
        return numberOfComments;
    }

    /**Creating WebDriver for the test
     *
     * @return - WebDriver
     */

    private static WebDriver getDriver() {
        LOGGER.info("Setting global property for driver");
        System.setProperty("webdriver.gecko.driver", "/Users/elinkin/Downloads/QA course/geckodriver");

        LOGGER.info("Opening Firefox driver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }
}
