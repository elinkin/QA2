package backendTesting;

import backendTesting.model.WeatherResponse;
import org.junit.Assert;
import org.junit.Test;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * Created by elinkin on 07/05/2017.
 */
public class WeatherTest {
    private static final Logger LOGGER = Logger.getLogger(WeatherTest.class);

    @Test
    public void weatherTest() throws IOException {
        WeatherRequester requester = new WeatherRequester();
        // Get response from server
        WeatherResponse response = requester.getWeather();
        // Check response
        Assert.assertEquals("Wrong LON: ", BigDecimal.valueOf(-0.13), response.getCoord().getLon());
        Assert.assertEquals("Wrong LAT: ", BigDecimal.valueOf(51.51), response.getCoord().getLat());
    }
}
