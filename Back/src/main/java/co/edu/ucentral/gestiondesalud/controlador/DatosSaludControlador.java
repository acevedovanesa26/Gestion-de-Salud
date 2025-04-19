package co.edu.ucentral.gestiondesalud.controlador;


import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.DatosSaludServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/health")
public class DatosSaludControlador {

    @Autowired
    private DatosSaludServicio service;

    @GetMapping
    public List<DatosSaludTwitter> getAllData() {
        return service.getAll();
    }

    @PostMapping("/save")
    public void saveData(@RequestBody List<DatosSaludTwitter> data) {
        service.saveAll(data);
    }
}