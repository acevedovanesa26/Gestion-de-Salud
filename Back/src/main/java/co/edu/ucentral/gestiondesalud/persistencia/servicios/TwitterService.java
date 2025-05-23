package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TwitterService {
    @Autowired
    private TweetRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Value("${twitter.bearer.token}")
    private String bearerToken;

    private String hacerConsulta(String query) {
        // Codifica la consulta para usarla en la URL
        String encodedQuery = URLEncoder.encode(query, StandardCharsets.UTF_8);
        String uri = "https://api.twitter.com/2/tweets/search/recent?query=" + encodedQuery + "&max_results=20";

        System.out.println("Query original: " + query);
        System.out.println("URL construida: " + uri);
        System.out.println("Encode Query: " + encodedQuery);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(bearerToken);
        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);
        return response.getBody();
    }

    public String buscarPorHashtag(String hashtag) {
        return hacerConsulta("#" + hashtag);
    }

    public String buscarPorMencion(String usuario) {
        return hacerConsulta("@" + usuario);
    }

    public String buscarPorFrase(String fraseExacta) {
        return hacerConsulta("\"" + fraseExacta + "\"");
    }

    public String buscarPorPalabra(String palabra) {
        return hacerConsulta(palabra);
    }



    public List<DatosSaludTwitter> obtenerTodos(String tema) {
        if (tema != null && !tema.isEmpty()) {
            return repository.findByTema(tema);
        } else {
            return repository.findAll();
        }
    }

    public List<String> obtenerTemasUnicos() {
        return mongoTemplate.getCollection("health_trends")
                .distinct("tema", String.class)
                .into(new ArrayList<>());
    }

    public Optional<DatosSaludTwitter> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public DatosSaludTwitter guardar(DatosSaludTwitter datos) {
        return repository.save(datos);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
