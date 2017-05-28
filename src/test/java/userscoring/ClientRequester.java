package userscoring;

import org.codehaus.jackson.map.ObjectMapper;
import userscoring.model.Client;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class ClientRequester {

    private String URL = "http://navipoint.lv:8080/qa2/getClients";

    public List<Client> getClients() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        List<Client> clients = Arrays.asList(mapper.readValue(new URL(URL), Client[].class));
        return clients;
    }
}
