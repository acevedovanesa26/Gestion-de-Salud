package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.DatosProcesadosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/datos-procesados")

public class DatosProcesadosController {

    @Autowired
    private DatosProcesadosService service;

    // Obtener todos o filtrar por tema
    @GetMapping
    public List<DatosProcesados> obtenerTodos(@RequestParam(required = false) String tema) {
        return service.obtenerTodos(tema);
    }

    // Obtener por ID
    @GetMapping("/{id}")
    public ResponseEntity<DatosProcesados> obtenerPorId(@PathVariable String id) {
        return service.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //Actualizar datos
    @PutMapping("/{id}")
    public ResponseEntity<DatosProcesados> actualizar(@PathVariable String id, @RequestBody DatosProcesados datos) {
        // Aseguramos que el objeto tenga el id correcto
        datos.setId(id);

        DatosProcesados actualizado = service.guardar(datos);

        if (actualizado == null) {
            // Aquí podrías devolver 404 si no se encontró el registro para actualizar
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(actualizado);
    }


    // Eliminar por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable String id) {
        if (service.obtenerPorId(id).isPresent()) {
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    // Obtener lista de temas únicos
    @GetMapping("/temas")
    public ResponseEntity<List<String>> obtenerTemas() {
        List<String> temas = service.obtenerTemasUnicos();
        return ResponseEntity.ok(temas);
    }

}
