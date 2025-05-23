package co.edu.ucentral.gestiondesalud.persistencia.repositorios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosProcesados;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface DatosProcesadosRepository extends MongoRepository<DatosProcesados, String> {
    List<DatosProcesados> findByTema(String tema);
}
