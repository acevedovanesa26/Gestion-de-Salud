package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.SentimientoEstadisticaDTO;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EstadisticasService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SentimientoEstadisticaDTO obtenerEstadisticas() {
        List<DatosProcesados> lista = mongoTemplate.findAll(DatosProcesados.class, "datos_procesados");
        return contarSentimientos(lista);
    }

    public SentimientoEstadisticaDTO obtenerEstadisticasPorTema(String tema) {
        Query query = new Query(Criteria.where("tema").is(tema));
        List<DatosProcesados> lista = mongoTemplate.find(query, DatosProcesados.class, "datos_procesados");
        return contarSentimientos(lista);
    }

    private SentimientoEstadisticaDTO contarSentimientos(List<DatosProcesados> lista) {
        // Debug: imprimir sentimientos
        lista.forEach(r -> System.out.println("Sentimiento encontrado: " + r.getSentimiento()));

        long positivos = lista.stream()
                .filter(r -> "Positivo".equalsIgnoreCase(r.getSentimiento()))
                .count();

        long negativos = lista.stream()
                .filter(r -> "Negativo".equalsIgnoreCase(r.getSentimiento()))
                .count();

        long neutros = lista.stream()
                .filter(r -> "Neutro".equalsIgnoreCase(r.getSentimiento()))
                .count();

        return new SentimientoEstadisticaDTO(positivos, negativos, neutros);
    }

    public List<Map<String, Object>> obtenerIngresosPorMes() {
        Aggregation agg = Aggregation.newAggregation(
                Aggregation.project()
                        .andExpression("year(fecha)").as("anio")
                        .andExpression("month(fecha)").as("mes"),
                Aggregation.group("anio", "mes").count().as("total"),
                Aggregation.sort(Sort.Direction.ASC, "_id.anio", "_id.mes")
        );

        AggregationResults<Map> resultados = mongoTemplate.aggregate(agg, "datos_procesados", Map.class);

        List<Map<String, Object>> respuesta = new ArrayList<>();
        for (Map resultado : resultados.getMappedResults()) {
            Map<String, Object> datos = new HashMap<>();
            Map<String, Integer> id = (Map<String, Integer>) resultado.get("_id");
            datos.put("anio", id.get("anio"));
            datos.put("mes", id.get("mes"));
            datos.put("total", resultado.get("total"));
            respuesta.add(datos);
        }
        return respuesta;
    }
}

