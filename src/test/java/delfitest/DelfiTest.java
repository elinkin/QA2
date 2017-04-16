package delfitest;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Testing comments on Delfi.lv.
 */

public class DelfiTest {

    private static final By COUNTER = By.className("comment-count");
    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private int commentCount;
    private final By regusercomments = By.xpath("//*[@id='comments-listing']/div[3]/a[1]/span");
    private final By anonusercomments = By.xpath("//*[@id='comments-listing']/div[3]/a[2]/span");

    /**
     * This test will test comment count on main page and article page.
     */

    @Test
    public void commentTesting () {
        LOGGER.info("We are starting our test");

        WebDriver driver = getDriver();

        LOGGER.info("We are opening rus.delfi.lv home page");
        driver.get("http://rus.delfi.lv");

        LOGGER.info("Getting comment count for the first article");
        commentCount = getCommentCount(driver);

        LOGGER.info("Open first article");
        WebElement news = driver.findElement(By.xpath("(//*[contains(@class, 'top2012-title')])"));
        news.click();

        LOGGER.info("Getting comment count from title on article page");
        Assert.assertEquals("Wrong comment count on article page", commentCount, getCommentCount(driver), 0);

        LOGGER.info("Comment count on article page is correct");

        LOGGER.info("Moving to article comment page");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement comments = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[contains(@class, 'comment-count')]")));
        comments.click();

        LOGGER.info("Getting comment count for registered users");
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement regcomments = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id='comments-listing']/div[3]/a[1]")));
        Integer regcountnumber = getregCommentCount(driver);

        LOGGER.info("Getting comment count for anonymous users");
        Integer anoncountnumber = getanonCommentCount(driver);

        LOGGER.info("Checking total comment count");
        Integer total = regcountnumber + anoncountnumber;
        Assert.assertEquals("Wrong comment count on comment page", commentCount, total, 0);

        LOGGER.info("Comment count on comment page is correct");

        LOGGER.info("We are closing our browser");
        driver.quit();
    }

    /**
     * Returns comment count of article
     *
     * @return - comment count
     */

    private Integer getCommentCount (WebDriver driver) {
        WebElement counter = driver.findElement(COUNTER);
        String counterString = counter.getText();
        Integer count = Integer.parseInt(counterString.substring(1, counterString.length() - 1));
        LOGGER.info("Comment count is " + count);
        return count;
    }

    /**
     * Returns registered user comment count from comments page
     *
     * @return - registered user comment count
     */

    private Integer getregCommentCount (WebDriver driver) {
        WebElement regcounter = driver.findElement(regusercomments);
        String regCounterStr = regcounter.getText();
        Integer regcount = Integer.parseInt(regCounterStr.substring(1, regCounterStr.length() - 1));
        LOGGER.info("Comment count is " + regcount);
        return regcount;
    }

    /**
     * Returns unregistered user comment count from comments page
     *
     * @return - unregistered user comment count
     */

    private Integer getanonCommentCount (WebDriver driver) {
        WebElement anoncounter = driver.findElement(anonusercomments);
        String anonCounterStr = anoncounter.getText();
        Integer anoncount = Integer.parseInt(anonCounterStr.substring(1, anonCounterStr.length() - 1));
        LOGGER.info("Comment count is " + anoncount);
        return anoncount;
    }

    /**Creating WebDriver for the test
     *
     * @return - WebDriver
     */

    private WebDriver getDriver() {
        LOGGER.info("Setting global property for driver");
        System.setProperty("webdriver.gecko.driver", "/Users/elinkin/Downloads/QA course/geckodriver");

        LOGGER.info("Opening Firefox driver");
        WebDriver driver = new FirefoxDriver();
        driver.manage().window().maximize();
        return driver;
    }

    /**Checking if first article has comments
     *
     * @return - 
     */
    public boolean elementHasClass(WebElement news, String counter) {
        return news.getAttribute("class").contains(counter);
    }
}
