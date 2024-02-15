package gamechoice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
public class GameService {

    private final String apiKey = "key=f22ad08ecb29499c9e72041b0dcc0f1e";
    private final String gameApiBaseUrl = "https://api.rawg.io/api";
    private final String apiPath = "&platforms=3&tags=multiplayer";
    private final int pageSize = 10;

    private final RestTemplate restTemplate;

    @Autowired
    public GameService(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
    }

    public String fetchRandomGame() {
        int randomPage = new Random().nextInt(10) + 1; // fix this later

        String apiUrl = gameApiBaseUrl + "/games?" + apiKey + apiPath + "&ordering=name&page=" + randomPage + "&page_size=" + pageSize;

        Map<String, Object> gamesResponse = restTemplate.getForObject(apiUrl, Map.class);
        List<Map<String, Object>> results = (List<Map<String, Object>>) gamesResponse.get("results");

        return getRandomGameFromResponse(results);
    }

    private String getRandomGameFromResponse(List<Map<String, Object>> games) {
        // randomly pick a game from the list
        int randomIndex = new Random().nextInt(pageSize);
        Map<String, Object> randomGame = games.get(randomIndex);

        String gameName = (String) randomGame.get("slug");

        return "Random Game: " + gameName;
    }
}
