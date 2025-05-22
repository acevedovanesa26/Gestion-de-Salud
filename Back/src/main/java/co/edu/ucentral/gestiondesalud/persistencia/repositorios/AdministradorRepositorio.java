package co.edu.ucentral.gestiondesalud.persistencia.repositorios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.Administrador;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface AdministradorRepositorio extends MongoRepository<Administrador, Integer> {
    Optional<Administrador> findByCorreo(String correo);
}