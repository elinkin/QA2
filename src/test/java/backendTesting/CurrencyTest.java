package backendTesting;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.util.Map;

public class CurrencyTest {

    private static final Logger LOGGER = Logger.getLogger(CurrencyTest.class);

    @Test
    public void currencyTest() throws IOException {
        CurrencyRequester requester = new CurrencyRequester();
        LOGGER.info("Get response from server");
        Map<String, Object> response = requester.loadCurrency();
        LOGGER.info("Check response");
        Assert.assertEquals("Wrong AUD rate: ", Double.valueOf(1.4809), response.get("AUD"));
    }
}
