package co.edu.ucentral.gestiondesalud.controlador;

import co.edu.ucentral.gestiondesalud.persistencia.servicios.CsvService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/csv")
public class CsvController {

    private final CsvService csvService;

    public CsvController(CsvService csvService) {
        this.csvService = csvService;
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadCsv(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "collection", defaultValue = "datos_kaggle") String collectionName,
            @RequestParam("tema") String tema) {

        Map<String, String> response = new HashMap<>();

        try {
            if (file.isEmpty() || !file.getOriginalFilename().toLowerCase().endsWith(".csv")) {
                response.put("message", "El archivo debe ser un CSV válido.");
                return ResponseEntity.badRequest().body(response);
            }

            csvService.saveDynamicCsv(file, collectionName, tema);
            response.put("message", "Datos cargados correctamente en la colección: " + collectionName);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            response.put("message", "Error al cargar el archivo CSV: " + e.getMessage());
            return ResponseEntity.internalServerError().body(response);
        }
    }


}
