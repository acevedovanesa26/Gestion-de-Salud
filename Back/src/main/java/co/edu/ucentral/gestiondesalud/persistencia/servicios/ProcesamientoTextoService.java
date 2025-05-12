// ProcesamientoTextoService.java
package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.TweetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.regex.*;

@Service
public class ProcesamientoTextoService {

    @Autowired
    private TweetRepository tweetRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    private final Set<String> stopwords = Set.of("de", "la", "que", "el", "en", "y", "a", "los", "del", "se", "las", "por", "un", "para");

    public void procesarTweets() {
        List<DatosSaludTwitter> tweets = tweetRepository.findAll();
        List<DatosProcesados> procesados = new ArrayList<>();

        for (DatosSaludTwitter tweet : tweets) {
            String textoOriginal = tweet.getText();
            String limpio = limpiarTexto(textoOriginal);
            DatosProcesados d = new DatosProcesados();
            d.setTextoOriginal(textoOriginal);
            d.setTextoProcesado(limpio);
            d.setFecha(tweet.getCreatedAt() != null ? tweet.getCreatedAt() : new Date());
            procesados.add(d);
        }

        mongoTemplate.insert(procesados, "datos_procesados");
    }

    private String limpiarTexto(String texto) {
        // Eliminar URLs, menciones, signos, emojis
        texto = texto.toLowerCase();
        texto = texto.replaceAll("https?://\\S+\\s?", "");
        texto = texto.replaceAll("@\\w+", "");
        texto = texto.replaceAll("#", "");
        texto = texto.replaceAll("[^\\p{L}\\s]", "");
        String[] palabras = texto.split("\\s+");

        StringBuilder limpio = new StringBuilder();
        for (String palabra : palabras) {
            if (!stopwords.contains(palabra)) {
                limpio.append(palabra).append(" ");
            }
        }
        return limpio.toString().trim();
    }
}
