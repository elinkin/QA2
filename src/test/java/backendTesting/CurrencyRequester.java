package backendTesting;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

public class CurrencyRequester {
    private String URL = "http://api.fixer.io/latest";

        public Map<String, Object> loadCurrency() throws IOException {
            Map<String, Object> responseMap = new ObjectMapper().readValue(new URL(URL), new TypeReference<Map<String, Object>>() {});
            return responseMap;
        }
    }

