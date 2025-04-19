package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TwitterService {
    @Value("${twitter.bearer.token}")
    private String bearerToken;


    public String buscarTweets(String query) {
        String uri = "https://api.twitter.com/2/tweets/search/recent?query=" + query + "&max_results=10";

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        System.out.println("Valor toke : " + bearerToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

        return response.getBody();
    }
}
