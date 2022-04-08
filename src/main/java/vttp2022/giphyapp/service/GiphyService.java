package vttp2022.giphyapp.service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;



@Service
public class GiphyService {
    private static Logger logger = Logger.getLogger(GiphyService.class.getName());
    
    //GIPHY_API_KEY
    @Value("${giphy.api.key}")
    private String giphyKey;

    private final String GIPHY_SEARCH = "https://api.giphy.com/v1/gifs/search";

    public List<String> getGifs(String search){
        return getGifs(search, 10 , "pg");
    }

    public List<String> getGifs(String search, String rating){
        return getGifs(search, 10 , rating);
    }

    public List<String> getGifs(String search, Integer limit){
        return getGifs(search, limit , "pg");
    }

    public List<String> getGifs(String search, Integer limit, String rating){
        List<String> result = new LinkedList<>();
        String url = UriComponentsBuilder.fromUriString(GIPHY_SEARCH)
                .queryParam("api_key", giphyKey)
                .queryParam("q", search)
                .queryParam("limit",limit)
                .queryParam("rating", rating)
                .toUriString();
        logger.log(Level.INFO,url);
        
        RequestEntity<Void> req = RequestEntity
                .get(url)
                .accept(MediaType.APPLICATION_JSON)
                .build();

        RestTemplate template = new RestTemplate();
        // ResponseEntity<String> resp = template.getForEntity(req, String.class);

        ResponseEntity<String> resp = template.exchange(req,String.class);  

        InputStream is = new ByteArrayInputStream(resp.getBody().getBytes());
        JsonReader reader = Json.createReader(is);
        JsonObject data = reader.readObject();
        JsonArray array = data.getJsonArray("data");
        
        for (int i = 0; i<array.size(); i++) {
            JsonObject object = (JsonObject) array.get(i);
            String imgUrl = object
                .getJsonObject("images")
                .getJsonObject("fixed_width")
                .getString("url");
            result.add(imgUrl);
        }
        // logger.log(Level.INFO, result.toString());
        return result;
    }
}
