package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.TweetRepository;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.TwitterService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/twitter")
public class TwitterController {

    @Autowired
    private TwitterService twitterService;

    @Autowired
    private TweetRepository tweetRepository;

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(@RequestParam String q) throws JsonProcessingException {
        String json = twitterService.buscarTweets(q);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        List<DatosSaludTwitter> lista = new ArrayList<>();

        for (JsonNode tweetNode : root.path("data")) {
            DatosSaludTwitter t = new DatosSaludTwitter();
            t.setId(tweetNode.get("id").asText());
            t.setText(tweetNode.get("text").asText());
            t.setCreatedAt(new Date()); // puedes usar tweetNode.get("created_at") si está disponible
            lista.add(t);
        }

        tweetRepository.saveAll(lista);

        return ResponseEntity.ok(lista);
    }
}
