package co.edu.ucentral.gestiondesalud.persistencia.servicios;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

@Service
public class CsvService {

    @Autowired
    private MongoTemplate mongoTemplate;
    public void saveDynamicCsv(MultipartFile file, String collectionName) {
        try (
                BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
                CSVParser parser = CSVFormat.DEFAULT
                        .withDelimiter(';') // <--- CAMBIO CLAVE AQUÍ
                        .withFirstRecordAsHeader()
                        .parse(reader)
        ) {
            List<Map<String, Object>> documents = new ArrayList<>();

            for (CSVRecord record : parser) {
                Map<String, Object> document = new LinkedHashMap<>();
                for (String header : parser.getHeaderMap().keySet()) {
                    document.put(header.trim(), record.get(header).trim()); // Opcional: limpiar espacios
                }
                documents.add(document);
            }

            mongoTemplate.insert(documents, collectionName);

        } catch (Exception e) {
            throw new RuntimeException("Error procesando el archivo CSV: " + e.getMessage(), e);
        }
    }

}
