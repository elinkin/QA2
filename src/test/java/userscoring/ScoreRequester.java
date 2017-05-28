package userscoring;

import org.codehaus.jackson.map.ObjectMapper;
import userscoring.model.Score;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ScoreRequester {

    private String URL = "http://navipoint.lv:8080/qa2/getScores";

    public List<Score> getScores() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Score> scores = Arrays.asList(mapper.readValue(new URL(URL), Score[].class));
        return scores;
    }
}
