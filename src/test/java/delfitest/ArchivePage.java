package delfitest;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * rus.delfi.lv archive page class.
 */
public class ArchivePage {
    BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(ArchivePage.class);
    private static By SEARCH_FIELD = By.name("query");

    public ArchivePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Archive page is loaded");
    }

    public CommentsPage openArticle(String article) throws InterruptedException {
        baseFunctions.fillInputAndSubmit(SEARCH_FIELD, article);
        baseFunctions.click(SEARCH_FIELD);
        LOGGER.info("We are opening article " + article);
        baseFunctions.click(By.partialLinkText(article));
        Thread.sleep(3000);
        return new CommentsPage(baseFunctions);
    }
}