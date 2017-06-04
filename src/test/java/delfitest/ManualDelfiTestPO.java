package delfitest;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

/**
 * Comparing comment count on Delfi.lv home page and counted manually on article comment page.
 */

public class ManualDelfiTestPO {

    private String[] articles = {
            "Брока: \"латышские партии\" будут сотрудничать в Рижской думе",
            "Дружим со всеми. Кучинскис про налоговый брак, выход из минусов санкций и сюрприз \"Металлурга\""
    };

    private static BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static final String WEB_SITE_URL = "rus.delfi.lv/archive/";

    @Test
    public void testDelfiComments() throws InterruptedException {
        LOGGER.info("We are opening rus.delfi.lv archive and searching for selected articles");
        for (String article : articles) {
            baseFunctions.goToUrl(WEB_SITE_URL);
            ArchivePage archivePage = new ArchivePage(baseFunctions);

            CommentsPage commentsPage = archivePage.openArticle(article);

            int numberOfArticlesFromHeader = commentsPage.getNumberOfCommentsFromHeader();
            int calculatedNumberOfComments = commentsPage.calculateNumberOfComments();
            Assert.assertEquals("blah", numberOfArticlesFromHeader, calculatedNumberOfComments);
        }
    }

    /**
     * Stopping webDriver
     */
    @AfterClass
    public static void closeDriver() {
        baseFunctions.stopDriver();
    }
}