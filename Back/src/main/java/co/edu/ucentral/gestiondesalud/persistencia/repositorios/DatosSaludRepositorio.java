package co.edu.ucentral.gestiondesalud.persistencia.repositorios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.List;

public interface DatosSaludRepositorio extends MongoRepository<DatosSaludTwitter, String> {
    List<DatosSaludTwitter> findBySource(String source);
    List<DatosSaludTwitter> findByKeywordsContaining(String keyword);
}
