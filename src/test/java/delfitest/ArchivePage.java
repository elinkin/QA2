package delfitest;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.openqa.selenium.By;

/**
 * rus.delfi.lv archive page class.
 */
public class ArchivePage {
    private String[] articles = {
            // works
            "\"Земля\" или \"Ежовая шубка\"? Банк Латвии выбирает монету года",
            // ???
            "В пятницу украинцы проведут в Риге акцию \"Остановите войну Путина\"",
            // test will fail, title - 156, real - reg:43, anon: 64 + 43 (2 deleted) + 7
            "СМИ: Опознан голос подозреваемого в причастности к крушению \"Боинга\" под Донецком"
    };

    BaseFunctions baseFunctions;
    private static final Logger LOGGER = Logger.getLogger(ArchivePage.class);
    private static By SEARCH_FIELD = By.name("query");

    public ArchivePage(BaseFunctions baseFunctions) {
        this.baseFunctions = baseFunctions;
        LOGGER.info("Archive page is loaded");
    }

    public CommentsPage openArticle() {
        for (String article : articles) {
            baseFunctions.fillInput(SEARCH_FIELD, article);
            baseFunctions.click(SEARCH_FIELD);

            LOGGER.info("We are opening article " + article);
            baseFunctions.click(By.partialLinkText(article));
        }
        return new CommentsPage(baseFunctions);
    }
}