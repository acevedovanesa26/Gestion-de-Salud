package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.DatosProcesadosService;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.TwitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health-trends")
public class DatosSinProcesarController {

    @Autowired
    private TwitterService service;

    @GetMapping
    public List<DatosSaludTwitter> obtenerTodos(@RequestParam(required = false) String tema) {
        return service.obtenerTodos(tema);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DatosSaludTwitter> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<DatosSaludTwitter> actualizar(@PathVariable String id, @RequestBody DatosSaludTwitter datos) {
        datos.setId(id);
        DatosSaludTwitter actualizado = service.guardar(datos);
        if (actualizado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(actualizado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/temas")
    public ResponseEntity<List<String>> obtenerTemas() {
        List<String> temas = service.obtenerTemasUnicos();
        return ResponseEntity.ok(temas);
    }
}
