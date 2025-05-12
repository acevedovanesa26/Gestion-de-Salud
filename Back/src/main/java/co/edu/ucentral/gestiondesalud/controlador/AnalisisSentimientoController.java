// AnalisisSentimientoController.java
package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.AnalisisSentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sentimiento")
public class AnalisisSentimientoController {

    @Autowired
    private AnalisisSentimientoService servicio;

    @PostMapping("/analizar")
    public ResponseEntity<String> analizar() {
        servicio.analizar();
        return ResponseEntity.ok("Análisis de sentimiento finalizado.");
    }
}
