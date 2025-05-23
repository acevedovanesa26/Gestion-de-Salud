package co.edu.ucentral.gestiondesalud.persistencia.repositorios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TweetRepository extends MongoRepository<DatosSaludTwitter, String> {
    List<DatosSaludTwitter> findByTema(String tema);
}
