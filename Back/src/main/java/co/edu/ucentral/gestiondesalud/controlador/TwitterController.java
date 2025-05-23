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
import org.springframework.web.client.HttpStatusCodeException;

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

    private List<DatosSaludTwitter> procesarYGuardar(String json, String tema) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(json);
        List<DatosSaludTwitter> lista = new ArrayList<>();

        for (JsonNode tweetNode : root.path("data")) {
            DatosSaludTwitter t = new DatosSaludTwitter();
            t.setId(tweetNode.get("id").asText());
            t.setText(tweetNode.get("text").asText());
            t.setCreatedAt(new Date()); // usar tweetNode.get("created_at") si Twitter lo incluye
            t.setTema(tema);
            lista.add(t);
        }

        tweetRepository.saveAll(lista);
        return lista;
    }

    @GetMapping("/buscar/hashtag")
    public ResponseEntity<?> buscarPorHashtag(@RequestParam String hashtag) {
        try {
            String json = twitterService.buscarPorHashtag(hashtag);
            return ResponseEntity.ok(procesarYGuardar(json, hashtag));
        } catch (HttpStatusCodeException e) {
            // Captura cualquier error HTTP (400, 401, 403, 429, 500, etc.)
            int statusCode = e.getRawStatusCode();
            String errorBody = e.getResponseBodyAsString();
            return ResponseEntity.status(statusCode).body(errorBody);
        } catch (JsonProcessingException e) {
            return ResponseEntity.status(500).body("{\"error\":\"Error procesando la respuesta JSON.\"}");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("{\"error\":\"Error interno inesperado.\"}");
        }
    }

    @GetMapping("/buscar/mencion")
    public ResponseEntity<?> buscarPorMencion(@RequestParam String usuario) throws JsonProcessingException {
        String json = twitterService.buscarPorMencion(usuario);
        return ResponseEntity.ok(procesarYGuardar(json, usuario));
    }

    @GetMapping("/buscar/frase")
    public ResponseEntity<?> buscarPorFrase(@RequestParam String frase) throws JsonProcessingException {
        String json = twitterService.buscarPorFrase(frase);
        return ResponseEntity.ok(procesarYGuardar(json, frase));
    }

    @GetMapping("/buscar/palabra")
    public ResponseEntity<?> buscarPorPalabra(@RequestParam String palabra) throws JsonProcessingException {
        String json = twitterService.buscarPorPalabra(palabra);
        return ResponseEntity.ok(procesarYGuardar(json, palabra));
    }
}

