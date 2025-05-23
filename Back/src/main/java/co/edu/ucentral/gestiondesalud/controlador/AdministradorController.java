package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.Administrador;
import co.edu.ucentral.gestiondesalud.persistencia.servicios.AdministradorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
public class AdministradorController {

    private final AdministradorService adminService;

    public AdministradorController(AdministradorService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registrar(@RequestBody Administrador admin) {
        String resultado = adminService.registrarse(admin);
        if (resultado.startsWith("Error")) {
            return ResponseEntity.badRequest().body(resultado);
        }
        return ResponseEntity.ok(resultado);
    }


    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Administrador admin) {
        boolean exito = adminService.iniciarSesion(admin.getCorreo(), admin.getPassword());

        Map<String, Object> response = new HashMap<>();

        if (!exito) {
            response.put("success", false);
            response.put("message", "Credenciales inválidas");
            return ResponseEntity.status(400).body(response);
        }

        // Si quieres enviar datos del usuario, búscalo (por ejemplo, por correo)
        Administrador administrador = adminService.buscarPorCorreo(admin.getCorreo());

        response.put("success", true);
        response.put("message", "Login exitoso");
        // Aquí agregas solo los datos que quieras exponer (ejemplo)
        Map<String, Object> userData = new HashMap<>();
        userData.put("id", administrador.getCedula());

        response.put("user", userData);

        return ResponseEntity.ok(response);
    }
}
