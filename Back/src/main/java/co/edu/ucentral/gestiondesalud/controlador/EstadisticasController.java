package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.SentimientoEstadisticaDTO;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.EstadisticasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticasController {

    @Autowired
    private EstadisticasService estadisticasService;

    // Endpoint que permite consultar estadísticas generales o por tema
    @GetMapping
    public SentimientoEstadisticaDTO obtenerEstadisticas(@RequestParam(required = false) String tema) {
        if (tema != null && !tema.trim().isEmpty()) {
            return estadisticasService.obtenerEstadisticasPorTema(tema.trim());
        } else {
            return estadisticasService.obtenerEstadisticas();
        }
    }
    @GetMapping("/ingresos-por-mes")
    public List<Map<String, Object>> obtenerIngresosPorMes() {
        return estadisticasService.obtenerIngresosPorMes();
    }

}
