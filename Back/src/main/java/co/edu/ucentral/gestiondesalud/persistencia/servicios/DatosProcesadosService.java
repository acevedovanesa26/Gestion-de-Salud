package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.DatosProcesadosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DatosProcesadosService {

    @Autowired
    private DatosProcesadosRepository repository;
    @Autowired
    private MongoTemplate mongoTemplate;

    public List<DatosProcesados> obtenerTodos(String tema) {
        if (tema != null && !tema.isEmpty()) {
            return repository.findByTema(tema);
        } else {
            return repository.findAll();
        }
    }
    // 🔥 Nuevo método para obtener temas únicos
    public List<String> obtenerTemasUnicos() {
        return mongoTemplate.getCollection("datos_procesados") // nombre real de tu colección
                .distinct("tema", String.class)
                .into(new ArrayList<>());
    }

    public Optional<DatosProcesados> obtenerPorId(String id) {
        return repository.findById(id);
    }

    public DatosProcesados guardar(DatosProcesados datos) {
        return repository.save(datos);
    }

    public void eliminar(String id) {
        repository.deleteById(id);
    }
}
