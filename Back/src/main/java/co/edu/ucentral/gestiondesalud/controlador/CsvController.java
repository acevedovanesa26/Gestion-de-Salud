package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadCsv(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "collection", defaultValue = "datos_kaggle") String collectionName) {

        try {
            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
                return ResponseEntity.badRequest().body("El archivo debe ser un CSV válido.");
            }

            csvService.saveDynamicCsv(file, collectionName);
            return ResponseEntity.ok("Datos cargados correctamente en la colección: " + collectionName);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error al cargar el archivo CSV: " + e.getMessage());
        }
    }
}
