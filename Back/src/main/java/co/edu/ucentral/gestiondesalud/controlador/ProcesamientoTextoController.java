package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.ProcesamientoTextoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/procesamiento")
public class ProcesamientoTextoController {

    private static final Logger logger = LoggerFactory.getLogger(ProcesamientoTextoController.class);

    private final ProcesamientoTextoService procesamientoTextoService;

    public ProcesamientoTextoController(ProcesamientoTextoService procesamientoTextoService) {
        this.procesamientoTextoService = procesamientoTextoService;
    }

    @PostMapping("/tweets")
    public ResponseEntity<Map<String, Object>> procesarTweets() {
        Map<String, Object> response = new HashMap<>();
        try {
            procesamientoTextoService.procesarTweets();
            response.put("success", true);
            response.put("message", "Procesamiento de tweets finalizado.");
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            logger.error("Error al procesar tweets", e);
            response.put("success", false);
            response.put("message", "Error al procesar tweets.");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
