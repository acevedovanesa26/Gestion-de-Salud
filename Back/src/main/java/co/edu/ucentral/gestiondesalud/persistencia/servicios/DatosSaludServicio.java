package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.DatosSaludRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DatosSaludServicio {

    @Autowired
    private DatosSaludRepositorio repository;

    public List<DatosSaludTwitter> getAll() {
        return repository.findAll();
    }

    public void saveAll(List<DatosSaludTwitter> data) {
        repository.saveAll(data);
    }
}