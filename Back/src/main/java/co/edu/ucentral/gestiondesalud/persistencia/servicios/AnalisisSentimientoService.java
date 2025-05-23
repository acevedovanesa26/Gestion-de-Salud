// AnalisisSentimientoService.java
package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.ResultadoSentimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalisisSentimientoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Listas simples, puedes ampliarlas según necesidad
    private final Set<String> positivas = Set.of("bueno", "excelente", "positivo", "feliz", "salud", "bien", "mejor", "esperanza", "normal", "estabilidad", "fuerte", "superado",
            "satisfactorio", "progreso", "prevención", "apoyo", "cuidado", "seguro", "sano", "tranquilo", "óptimo", "eficaz", "mejora", "curado", "contento");
    private final Set<String> negativas = Set.of(
            "malo", "terrible", "negativo", "enfermo", "peor", "mal", "grave",
            "crítico", "dolor", "infección", "riesgo", "empeorando", "hospitalizado",
            "urgencia", "temor", "miedo", "triste", "débil", "colapso", "negligencia",
            "saturado", "abandono", "contagio", "crisis", "problema", "emergencia","violento"
    );
    public void analizarPorTema(String tema) {
        // Busca solo documentos que contengan el tema en textoProcesado
        Query query = new Query(Criteria.where("textoProcesado").regex("(?i).*" + tema + ".*")); // (?i) para case-insensitive
        List<DatosProcesados> textos = mongoTemplate.find(query, DatosProcesados.class, "datos_procesados");

        for (DatosProcesados dp : textos) {
            int score = calcularScore(dp.getTextoProcesado());
            String sentimiento = interpretar(score);

            dp.setScore(score);
            dp.setSentimiento(sentimiento);
            dp.setTema(tema); // Actualiza o asigna el tema recibido

            mongoTemplate.save(dp, "datos_procesados");
        }
    }

    public void analizar() {
        List<DatosProcesados> textos = mongoTemplate.findAll(DatosProcesados.class, "datos_procesados");

        for (DatosProcesados dp : textos) {
            int score = calcularScore(dp.getTextoProcesado());
            String sentimiento = interpretar(score);

            // Actualizar documento con score y sentimiento
            Query query = new Query(Criteria.where("_id").is(dp.getId()));
            org.springframework.data.mongodb.core.query.Update update = new org.springframework.data.mongodb.core.query.Update()
                    .set("score", score)
                    .set("sentimiento", sentimiento);

            // Mantener el tema si existe
            if (dp.getTema() != null) {
                update.set("tema", dp.getTema());
            }

            mongoTemplate.updateFirst(query, update, "datos_procesados");
        }
    }


    private int calcularScore(String texto) {
        int score = 0;
        for (String palabra : texto.split("\\s+")) {
            if (positivas.contains(palabra)) {
                score++;
            } else if (negativas.contains(palabra)) {
                score--;
            }
        }
        return score;
    }

    private String interpretar(int score) {
        if (score > 0) {
            return "Positivo";
        } else if (score < 0) {
            return "Negativo";
        } else {
            return "Neutro";
        }
    }

}
