// EstadisticaSentimientoController.java
package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.SentimientoEstadisticaDTO;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.EstadisticaSentimientoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/estadisticas")
public class EstadisticaSentimientoController {

    @Autowired
    private EstadisticaSentimientoService service;

    @GetMapping("/sentimientos")
    public ResponseEntity<SentimientoEstadisticaDTO> obtenerEstadisticas() {
        return ResponseEntity.ok(service.obtenerEstadisticas());
    }
}
