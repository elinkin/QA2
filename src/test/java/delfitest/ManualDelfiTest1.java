package delfitest;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class ManualDelfiTest1 {
    private String[] articles = {
            // works
            "\"Земля\" или \"Ежовая шубка\"? Банк Латвии выбирает монету года",
            // ???
            "В пятницу украинцы проведут в Риге акцию \"Остановите войну Путина\"",
            // test will fail, title - 156, real - reg:43, anon: 64 + 43 (2 deleted) + 7
            "СМИ: Опознан голос подозреваемого в причастности к крушению \"Боинга\" под Донецком"
    };

    private static WebDriver driver;

    @BeforeClass
    public static void setUp() {
        System.setProperty("webdriver.gecko.driver", "/Users/elinkin/Downloads/QA course/geckodriver");
        driver = new FirefoxDriver();
    }

    @AfterClass
    public static void tearDown() {
        driver.quit();
    }

    @Test
    public void testDelfiComments() throws InterruptedException, IOException {
        for (String article : articles) {
            driver.get("http://rus.delfi.lv/archive/");
            WebElement searchField = driver.findElement(By.name("query"));
            searchField.sendKeys(article);
            searchField.submit();

            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(ExpectedConditions.elementToBeClickable(By.partialLinkText(article)));
            WebElement link = driver.findElement(By.partialLinkText(article));
            link.click();
            wait.until(ExpectedConditions.elementToBeClickable(By.className("comment-count")));
            checkPage();
        }
    }

    private void checkPage() throws InterruptedException {
        WebDriverWait wait = new WebDriverWait(driver, 10);

        WebElement element = driver.findElements(By.className("comment-count")).get(0);
        String text = element.getText().replaceAll("[^0-9]", "");
        int total = Integer.parseInt(text);
        System.out.println(String.format("Total: %d", total));
        element.click();

        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comments-list")));

        int registered = countAllComments();
        System.out.println(String.format("Registered: %d", registered));

        driver.findElement(By.className("comment-thread-switcher-list-a-anon")).click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.className("comment-thread-switcher-selected-anon")));

        int anonymous = countAllComments();
        System.out.println(String.format("Anonymous: %d", anonymous));

        assertEquals(total, registered + anonymous);
    }

    private int countAllComments() throws InterruptedException {
        int count = 0;
        do {
            expandComments();
            // comment-avatar
            int size = driver.findElements(By.className("comment-content-inner")).size();
            System.out.println("page size " + size);
            count += size;
        } while (nextPage());
        return count;
    }

    private boolean nextPage() {
        List<WebElement> elements = driver.findElements(By.className("comments-pager-arrow-last"));
        if (elements.size() > 0) {
            final String text = driver.findElement(By.className("comment-main-title-link")).getText();
            elements.get(0).click();
            WebDriverWait wait = new WebDriverWait(driver, 10);
            wait.until(ExpectedConditions.stalenessOf(elements.get(0)));
//            System.out.println("old hidden");
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comments-list")));
//            System.out.println("new found");
            return true;
        }
        return false;
    }

    private void expandComments() throws InterruptedException {
        List<WebElement> elements = driver.findElements(By.className("load-more-comments-btn-link"));
        for (final WebElement element : elements) {
            Integer loadedComments = 0;
            Integer totalComments = Integer.valueOf(element.getAttribute("data-quote-count"));
//            System.out.println(String.format("loaded: %d, total=%d", loadedComments, totalComments));
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
//                Thread.sleep(1000); // FFS
            } while (loadedComments < totalComments);
//            Thread.sleep(1000); // FFS
        }
    }
}

