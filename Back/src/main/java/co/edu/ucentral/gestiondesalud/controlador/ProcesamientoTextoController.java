// ProcesamientoTextoController.java
package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.ProcesamientoTextoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/procesamiento")
public class ProcesamientoTextoController {

    @Autowired
    private ProcesamientoTextoService procesamientoTextoService;

    @PostMapping("/tweets")
    public ResponseEntity<String> procesarTweets() {
        procesamientoTextoService.procesarTweets();
        return ResponseEntity.ok("Procesamiento de tweets finalizado.");
    }
}
