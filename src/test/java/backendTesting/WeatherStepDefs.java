package backendTesting;

import backendTesting.model.WeatherResponse;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Assert;

import java.io.IOException;
import java.math.BigDecimal;

public class WeatherStepDefs {
    private WeatherRequester requester = new WeatherRequester();
    private WeatherResponse response = new WeatherResponse();

    @When("We are requesting weather info")
    public void get_weather_info() throws IOException {
        response = requester.getWeather();
    }

    @Then("LON should be (.*)")
    public void check_lon(BigDecimal lon) {
        Assert.assertEquals("Wrong LON: ", BigDecimal.valueOf(-0.13), response.getCoord().getLon());
    }

    @Then("LAT should be (.*)")
    public void check_lat(BigDecimal lat) {
        Assert.assertEquals("Wrong LAT: ", BigDecimal.valueOf(51.51), response.getCoord().getLat());
    }
}
