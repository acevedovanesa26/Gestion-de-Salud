package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.Administrador;
import co.edu.ucentral.gestiondesalud.persistencia.repositorios.AdministradorRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Optional;


@Service
public class AdministradorService {

    @Autowired
    private AdministradorRepositorio repository;

    public String registrarse(Administrador admin) {
        if (repository.findByCorreo(admin.getCorreo()).isPresent()) {
            return "Error: Correo ya registrado";
        }

        String contraseña_encriptada= encriptar(admin.getPassword());
        admin.setPassword(contraseña_encriptada);
        repository.save(admin);


        return "Administrador reigstrado exitosamente";
    };

    public boolean iniciarSesion(String correo, String password) {
        Optional<Administrador> optionalAdmin = repository.findByCorreo(correo);

        if (optionalAdmin.isPresent()) {
            Administrador admin = optionalAdmin.get();
            System.out.println(admin.getPassword());
            System.out.println(password);
            if (passwordEncoder.matches(password, admin.getPassword())) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
    public Administrador buscarPorCorreo(String correo) {
        Optional<Administrador> optionalAdmin = repository.findByCorreo(correo);
        Administrador admin = optionalAdmin.get();
        return admin;
    }


    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    // Método para encriptar una contraseña
    public static String encriptar(String password) {
        return passwordEncoder.encode(password);
    }

    // Método para verificar si la contraseña ingresada coincide con la encriptada
    public static boolean verificar(String passwordPlano, String passwordEncriptado) {
        return passwordEncoder.matches(passwordPlano, passwordEncriptado);
    }

}
