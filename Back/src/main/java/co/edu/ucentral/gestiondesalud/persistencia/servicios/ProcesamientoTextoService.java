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
        Set<String> textosExistentes = new HashSet<>();

        // Cargar textos procesados existentes desde la BD
        List<DatosProcesados> yaProcesados = mongoTemplate.findAll(DatosProcesados.class, "datos_procesados");
        for (DatosProcesados dp : yaProcesados) {
            if (dp.getTextoProcesado() != null) {
                textosExistentes.add(dp.getTextoProcesado().trim().toLowerCase());
            }
        }

        List<DatosProcesados> procesados = new ArrayList<>();

        for (DatosSaludTwitter tweet : tweets) {
            String textoOriginal = tweet.getText();
            String limpio = limpiarTexto(textoOriginal);

            String clave = limpio.trim().toLowerCase();
            if (!textosExistentes.contains(clave)) {
                DatosProcesados d = new DatosProcesados();
                d.setTextoOriginal(textoOriginal);
                d.setTextoProcesado(limpio);
                d.setFecha(tweet.getCreatedAt() != null ? tweet.getCreatedAt() : new Date());
                d.setTema(tweet.getTema());
                procesados.add(d);
                textosExistentes.add(clave); // también evitar duplicados dentro del mismo lote
            }
        }

        if (!procesados.isEmpty()) {
            mongoTemplate.insert(procesados, "datos_procesados");
            System.out.println("Insertados: " + procesados.size() + " nuevos documentos.");
        } else {
            System.out.println("No se insertaron nuevos documentos (todos duplicados).");
        }
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
