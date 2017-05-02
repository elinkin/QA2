package delfitest;

import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Comparing comment count on Delfi.lv home page and counted manually on article comment page.
 */

public class ManualDelfiTest {
    private String[] articles = {
            // works
            "\"Земля\" или \"Ежовая шубка\"? Банк Латвии выбирает монету года",
            // ???
            "В пятницу украинцы проведут в Риге акцию \"Остановите войну Путина\"",
            // test will fail, title - 156, real - reg:43, anon: 64 + 43 (2 deleted) + 7
            "СМИ: Опознан голос подозреваемого в причастности к крушению \"Боинга\" под Донецком"
    };

    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static WebDriver driver;
    private static By regUserPage = By.xpath("//div[@id='comment-dark-skin-wrapper']/div[2]/form/a[2]");
    private static By anonUserPage = By.xpath("//a[contains(@class, 'comment-thread-switcher-list-a-anon')]");

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
    public void commentTest() {
        List<Details> details = loadArticles();
        LOGGER.info(details);

    }

    @Test
    public void testDelfiComments() throws InterruptedException {
        LOGGER.info("We are opening rus.delfi.lv archive and searching for selected articles");
        for (String article : articles) {
            driver.get("http://rus.delfi.lv/archive/");
            WebElement searchField = driver.findElement(By.name("query"));
            searchField.sendKeys(article);
            searchField.submit();

            LOGGER.info("We are opening selected articles");
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(article)));
            WebElement link = driver.findElement(By.partialLinkText(article));
            LOGGER.info("We are opening article " + article);
            link.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.className("comment-count")));
            checkPage();
        }
    }

    private void checkPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        LOGGER.info("Getting total number of comments from the article page");
        WebElement element = driver.findElements(By.className("comment-count")).get(0);
        String text = element.getText().replaceAll("[^0-9]", "");
        int total = Integer.parseInt(text);
        System.out.println(String.format("Total number of comments: ", total));
        LOGGER.info("Opening registered user comments page");
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comments-list")));

        LOGGER.info("Counting comments on all registered user comments pages");
        int registered = countAllComments();
        System.out.println(String.format("Total comment count for registered users is: %d", registered));

        LOGGER.info("Opening anonymous comments page");
        driver.findElement(By.className("comment-thread-switcher-list-a-anon")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("comment-thread-switcher-selected-anon")));

        LOGGER.info("Counting comments on all anonymous user comments pages");
        int anonymous = countAllComments();
        System.out.println(String.format("Total comment count for registered users is: %d", anonymous));

        int totalManual = registered + anonymous;
        LOGGER.info("Total comment count for anonymous and registered users is: " + totalManual);
        assertEquals(total, registered + anonymous);
    }

    /**
     * Opens registered user comments page
     */

    private void loadRegCommentsPage() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.elementToBeClickable(regUserPage));
        WebElement element = driver.findElement(regUserPage);
        element.click();
    }

    /**
     * Opens anonymous comments page
     */

    private void switchToAnonymousComments() {
        WebElement element = driver.findElement(anonUserPage);
        element.click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//div[contains(@class, 'comment-thread-switcher-selected-anon')]")));
    }

    /**
     * Returns number of comments on the page
     *
     * @return - number of comments
     */

    public int countCommentsOnPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//*[@id='comments-listing']/div[3]")));
        LOGGER.info("Expanding all comments");
        expandComments();

        return driver.findElements(By.xpath("//div[@class='comment-content-inner']")).size();
    }

    /**
     * Returns number of comments on all pages per user type
     *
     * @return - total number of comments
     */

    private int countAllComments() throws InterruptedException {
        int totalComments = 0;
        do {
            expandComments();
            int pageComments = driver.findElements(By.className("comment-content-inner")).size();
            LOGGER.info("Number of comments on the current page " + pageComments);
            totalComments += pageComments;
        } while (nextPage());
        return totalComments;
    }

    /**
     * Expands all comments on the page
     */

    private void expandComments() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("load-more-comments-btn-link"));
        for (final WebElement element : elements) {
            Integer loadedComments;
            Integer totalComments = Integer.valueOf(element.getAttribute("data-quote-count"));

            do {
                final String before = element.getAttribute("data-loaded-comments");
                new WebDriverWait(driver, 30).until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                new WebDriverWait(driver, 30).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webDriver) {
                        return !element.getAttribute("data-loaded-comments").equals(before);
                    }
                });
                loadedComments = Integer.valueOf(element.getAttribute("data-loaded-comments"));
            } while (loadedComments < totalComments);
        }
    }

    /**
     * Waits for the page to load completely
     */

    private void clickAndWait(By xpath) {
        WebElement element = driver.findElement(xpath);
        element.click();

        WebDriverWait wait = new WebDriverWait(driver, 120);
        // make sure we left current page
        wait.until(ExpectedConditions.stalenessOf(element));
        // make sure new page is fully loaded
        wait.until(new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver webDriver) {
                return (Boolean) ((JavascriptExecutor) driver).executeScript("return document.readyState == 'complete'");
            }
        });
    }

    /**
     * Loads next page of comments until there are no more pages to load
     */

    private boolean nextPage() {
        List<WebElement> elements = driver.findElements(By.className("comments-pager-arrow-last"));
        if (elements.size() > 0) {
            final String text = driver.findElement(By.className("comment-main-title-link")).getText();
            elements.get(0).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.stalenessOf(elements.get(0)));
            // make sure we left current page
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comments-list")));
            // make sure we are on the new page
            return true;
        }
        return false;
    }

    /**
     * Returns array of article titles and their respective number of comments
     *
     * @return - array of articles and comments
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