// EstadisticaSentimientoService.java
package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.SentimientoEstadisticaDTO;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.ResultadoSentimiento;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstadisticaSentimientoService {

    @Autowired
    private MongoTemplate mongoTemplate;

    public SentimientoEstadisticaDTO obtenerEstadisticas() {
        List<ResultadoSentimiento> lista = mongoTemplate.findAll(ResultadoSentimiento.class, "sentimientos");

        long positivos = lista.stream().filter(r -> "Positivo".equalsIgnoreCase(r.getSentimiento())).count();
        long negativos = lista.stream().filter(r -> "Negativo".equalsIgnoreCase(r.getSentimiento())).count();
        long neutros = lista.stream().filter(r -> "Neutro".equalsIgnoreCase(r.getSentimiento())).count();

        return new SentimientoEstadisticaDTO(positivos, negativos, neutros);
    }
}
