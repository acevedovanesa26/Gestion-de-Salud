package co.edu.ucentral.gestiondesalud.persistencia.repositorios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TweetRepository extends MongoRepository<DatosSaludTwitter, String> {
}
