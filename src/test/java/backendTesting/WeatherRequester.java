package backendTesting;

import backendTesting.model.WeatherResponse;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URL;

/**
 * Created by elinkin on 07/05/2017.
 */
public class WeatherRequester {

    private String URL = "http://samples.openweathermap.org/data/2.5/weather?q=London,uk&appid=b1b15e88fa797225412429c1c50c122a1";

    public WeatherResponse getWeather() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        WeatherResponse response = mapper.readValue(new URL(URL), WeatherResponse.class);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(URL, String.class);
        String jsonResponse = responseEntity.getBody();
        WeatherResponse response1 = mapper.readValue(jsonResponse, WeatherResponse.class);
        return response;
    }
}
