package delfitest;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * rus.delfi.lv article page class.
 */
public class CommentsPage {
    private BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(CommentsPage.class);

    public CommentsPage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Article page opened");
    }

    public int getNumberOfCommentsFromHeader() throws InterruptedException {
        LOGGER.info("Getting total number of comments from the article page");
        String text1 = baseFunctions.findElements(By.className("comment-count")).get(0).getText();
        String text = text1.replaceAll("[^0-9]", "");
        return Integer.parseInt(text);
    }

    public int calculateNumberOfComments() throws InterruptedException {
        LOGGER.info("Opening registered user comments page");
        baseFunctions.click(By.className("comment-count"));

        baseFunctions.waitForElement(By.id("comments-list"), 1000);

        LOGGER.info("Counting comments on all registered user comments pages");
        int registered = countAllComments();
        System.out.println(String.format("Total comment count for registered users is: %d", registered));

        LOGGER.info("Opening anonymous comments page");
        baseFunctions.click(By.className("comment-thread-switcher-list-a-anon"));
        baseFunctions.waitForElement(By.className("comment-thread-switcher-selected-anon"), 1000);

        LOGGER.info("Counting comments on all anonymous user comments pages");
        int anonymous = countAllComments();
        System.out.println(String.format("Total comment count for registered users is: %d", anonymous));

        return registered + anonymous;
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
            int pageComments = baseFunctions.findElements(By.className("comment-content-inner")).size();
            LOGGER.info("Number of comments on the current page " + pageComments);
            totalComments += pageComments;
        } while (nextPage());
        return totalComments;
    }

    /**
     * Expands all comments on the page
     */
    private void expandComments() throws InterruptedException {
        List<WebElement> elements = baseFunctions.findElements(By.className("load-more-comments-btn-link"));
        for (final WebElement element : elements) {
            Integer loadedComments;
            Integer totalComments = Integer.valueOf(element.getAttribute("data-quote-count"));
            do {
                final String before = element.getAttribute("data-loaded-comments");
                new WebDriverWait(baseFunctions.getDriver(), 30).until(ExpectedConditions.elementToBeClickable(element));
                element.click();
                new WebDriverWait(baseFunctions.getDriver(), 30).until(new ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver webDriver) {
                        return !element.getAttribute("data-loaded-comments").equals(before);
                    }
                });
                loadedComments = Integer.valueOf(element.getAttribute("data-loaded-comments"));
            } while (loadedComments < totalComments);
        }
    }

    /**
     * Loads next page of comments until there are no more pages to load
     */
    private boolean nextPage() {
        List<WebElement> elements = baseFunctions.findElements(By.className("comments-pager-arrow-last"));
        if (elements.size() > 0) {
            elements.get(0).click();
            WebDriverWait wait = new WebDriverWait(baseFunctions.getDriver(), 10);
            wait.until(ExpectedConditions.stalenessOf(elements.get(0)));
            // make sure we left current page
            wait.until(ExpectedConditions.presenceOfElementLocated(By.id("comments-list")));
            // make sure we are on the new page
            return true;
        }
        return false;
    }
}
