package delfitest;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

/**
 * Comparing comment count on Delfi.lv home page and counted manually on article comment page.
 */

public class ManualDelfiTest {
    private String[] articles = {
            "Зарплата главы СГД за полтора месяца составила 5 944 евро",
            "ВИДЕО: ФСБ задержала одного из организаторов теракта в метро Петербурга"
    };

    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        LOGGER.info("We are starting our test");
        driver = getDriver();
    }

    @AfterClass
    public static void tearDown() {
        LOGGER.info("We are closing our browser");
        driver.quit();
    }

    /**
     * This test will test comment count on main page and article page.
     */

    @Test
    public void commentTesting() {
        List<Details> details = loadArticles();
        LOGGER.info(details);

    }

    @Test
    public void commentCounting() throws InterruptedException {
        LOGGER.info("Opening selected article");
        driver.get("http://rus.delfi.lv/news/daily/latvia/zarplata-gendirektora-sgd-za-poltora-mesyaca-sostavila-5-944-evro.d?id=48739375");

        LOGGER.info("Opening comments page");
        WebElement readComments = driver.findElement(By.xpath("//div[@id='comment-dark-skin-wrapper']/div[2]/form/a[2]"));
        readComments.click();

        LOGGER.info("Expanding all comments");
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement expand = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='load-more-comments-btn']")));
        expand.click();

        Thread.sleep(1000);

        List regComments = new ArrayList();
        List<WebElement> comments = driver.findElements(By.xpath("//*[contains(@class, 'comment-avatar-registered')]"));
        regComments.addAll(comments);
        int commentCount = regComments.size();
        LOGGER.info("Registered comment count is " + commentCount);

        /* same for anonymous
         */
        LOGGER.info("Opening anonymous comments page");
        WebElement readComments1 = driver.findElement(By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-anon')]"));
        readComments1.click();

        LOGGER.info("Expanding all comments");
        WebDriverWait wait1 = new WebDriverWait(driver, 10);
        WebElement expandButton = wait1.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class='load-more-comments-btn']")));
        List<WebElement> expand1 = driver.findElements(By.xpath("//div[@class='load-more-comments-btn']"));
        for (WebElement element : expand1) {
            element.click();
        }

        Thread.sleep(1000);

        List anonComments = new ArrayList();
        List<WebElement> comments1 = driver.findElements(By.xpath("//div[contains(@class, 'comment-post-full-content')]"));
        anonComments.addAll(comments1);
        int commentCount1 = comments1.size();
        LOGGER.info("Anonymous comment count is " + commentCount1);
    }

    /**
     * Returns array of article titles and their respective number of comments
     */

    private List<Details> loadArticles() {
        List<Details> details = new ArrayList<Details>();
        LOGGER.info("We are opening rus.delfi.lv home page");
        driver.get("http://rus.delfi.lv");
        List<WebElement> elements = driver.findElements(By.xpath("//h3[@class='top2012-title']"));
        for (WebElement element : elements) {
            WebElement articleElement = element.findElement(By.xpath("(./a)[1]"));
            String article = articleElement.getText();
            LOGGER.info("Article title is " + article);
            int numberOfComments = 0;
            try {
                WebElement commentsElement = element.findElement(By.xpath("(./a)[2]"));
                numberOfComments = loadCommentCount(commentsElement);
            } catch (NoSuchElementException e) {
                LOGGER.info("Can't parse number of comments");
            }

            for (String s : articles) {
                if (s.equals(article)) {
                    String url = articleElement.getAttribute("href");
                    Details d = new Details(url, numberOfComments);
                    details.add(d);
                    break;
                }
            }
        }
        return details;
    }

    /**
     * Returns comment count of an article
     *
     * @return - number of comments
     */

    private Integer loadCommentCount(WebElement element) {
        String counterString = element.getText();
        Integer numberOfComments = Integer.parseInt(counterString.substring(1, counterString.length() - 1));
        LOGGER.info("Comment count is " + numberOfComments);
        return numberOfComments;
    }

    /**
     * Returns link and comment count for the provided article
     *
     * @return - article URL and number of comments
     */

    private class Details {
        String url;
        int numberOfComments;

        Details(String url, int numberOfComments) {
            this.url = url;
            this.numberOfComments = numberOfComments;
        }

        @Override
        public String toString() {
            return "Details{" +
                    "url='" + url + '\'' +
                    ", numberOfComments=" + numberOfComments +
                    '}';
        }

    }


    /**
     * Creating WebDriver for the test
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