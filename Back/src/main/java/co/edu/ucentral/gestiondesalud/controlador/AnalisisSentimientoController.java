package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.AnalisisSentimientoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sentimiento")
public class AnalisisSentimientoController {

    private static final Logger logger = LoggerFactory.getLogger(AnalisisSentimientoController.class);

    private final AnalisisSentimientoService servicio;

    public AnalisisSentimientoController(AnalisisSentimientoService servicio) {
        this.servicio = servicio;
    }

    @PostMapping("/analizar")
    public ResponseEntity<Map<String, Object>> analizarPorTema(@RequestParam(required = false) String tema) {
        Map<String, Object> response = new HashMap<>();
        try {
            if (tema != null && !tema.isBlank()) {
                servicio.analizarPorTema(tema.toLowerCase());
                response.put("success", true);
                response.put("message", "Análisis de sentimiento para el tema '" + tema + "' finalizado.");
            } else {
                servicio.analizar(); // Análisis general (sin tema)
                response.put("success", true);
                response.put("message", "Análisis de sentimiento general finalizado.");
            }
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al analizar sentimiento", e);
            response.put("success", false);
            response.put("message", "Error al realizar el análisis de sentimiento.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
