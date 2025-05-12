// AnalisisSentimientoService.java
package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.ResultadoSentimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AnalisisSentimientoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    // Listas simples, puedes ampliarlas según necesidad
    private final Set<String> positivas = Set.of("bueno", "excelente", "positivo", "feliz", "salud", "bien", "mejor");
    private final Set<String> negativas = Set.of("malo", "terrible", "negativo", "enfermo", "peor", "mal", "grave");

    public void analizar() {
        List<DatosProcesados> textos = mongoTemplate.findAll(DatosProcesados.class, "datos_procesados");
        List<ResultadoSentimiento> resultados = new ArrayList<>();

        for (DatosProcesados dp : textos) {
            String texto = dp.getTextoProcesado();
            int score = calcularScore(texto);
            String sentimiento = interpretar(score);

            ResultadoSentimiento rs = new ResultadoSentimiento();
            rs.setTexto(dp.getTextoProcesado());
            rs.setSentimiento(sentimiento);
            rs.setScore(score);
            rs.setFecha(dp.getFecha());
            resultados.add(rs);
        }

        mongoTemplate.insert(resultados, "sentimientos");
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
        if (score > 0) return "Positivo";
        else if (score < 0) return "Negativo";
        else return "Neutro";
    }
}
