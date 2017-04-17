package delfitest;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import java.lang.Math;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * Comparing contents on desktop and mobile Delfi.lv.
 */

public class MobileDelfiTest {

    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static final int MAX_POSTS = 5;

    /**
     * This test will test comment count on main page and article page.
     */

    @Test
    public void commentTesting () {
        LOGGER.info("We are starting our test");

        WebDriver driver = getDriver();

        LOGGER.info("We are opening rus.delfi.lv home page");
        driver.get("http://rus.delfi.lv");

        LOGGER.info("Getting comment count for desktop articles");
        List<Integer> desktopCounters = getComments(driver, By.xpath("//*[@class='comment-count']"), MAX_POSTS);
        System.out.println("The arraylist contains the following elements: " + desktopCounters);

        LOGGER.info("We are opening mobile rus.delfi.lv home page");
        driver.get("http://m.rus.delfi.lv");

        LOGGER.info("Getting comment count for mobile articles");
        List<Integer> mobileCounters = getComments(driver, By.xpath("//*[@class='commentCount']"), MAX_POSTS);
        System.out.println("The arraylist contains the following elements: " + mobileCounters);

        assertThat(desktopCounters, is(mobileCounters));
        LOGGER.info("Comment count for desktop and mobile version is correct");

        LOGGER.info("We are closing our browser");
        driver.quit();
    }

    /**
     * Returns comment count of 5 first articles, put in an array
     * */

    private List<Integer> getComments (WebDriver driver, By count, int limit) {
        List<WebElement> counter = driver.findElements(count);
        List<Integer> myList = new ArrayList<Integer>();
        int size = Math.min(counter.size(), limit);
        for (int i = 0; i < size; i++) {
            WebElement ele = counter.get(i);
            String eleString = ele.getText();
            Integer numberOfComments = Integer.parseInt(eleString.substring(1, eleString.length() - 1));
            myList.add(numberOfComments);
        }
        return myList;
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
}