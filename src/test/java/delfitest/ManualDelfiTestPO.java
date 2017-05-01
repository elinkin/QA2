package delfitest;

import core.BaseFunctions;
import org.apache.log4j.Logger;
import org.junit.Test;

/**
 * Comparing comment count on Delfi.lv home page and counted manually on article comment page.
 */

public class ManualDelfiTestPO {

    private BaseFunctions baseFunctions = new BaseFunctions();
    private static final Logger LOGGER = Logger.getLogger(DelfiTest.class);
    private static final String WEB_SITE_URL = "rus.delfi.lv/archive/";

    @Test
    public void testDelfiComments() throws InterruptedException {
        LOGGER.info("We are opening rus.delfi.lv archive and searching for selected articles");
        baseFunctions.goToUrl(WEB_SITE_URL);

        LOGGER.info("Opening anonymous comments page");
    }
}