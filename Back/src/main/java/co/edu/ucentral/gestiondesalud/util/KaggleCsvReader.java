package co.edu.ucentral.gestiondesalud.util;

import co.edu.ucentral.gestiondesalud.persistencia.entidades.DatosSaludTwitter;
import org.apache.commons.csv.*;
import java.io.*;
import java.nio.file.*;
import java.util.*;

public class KaggleCsvReader {

    public static List<DatosSaludTwitter> read(Path path) {
        List<DatosSaludTwitter> dataList = new ArrayList<>();
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .parse(reader);

            for (CSVRecord r : records) {
                DatosSaludTwitter data = new DatosSaludTwitter();
                data.setSource("kaggle");
                data.setText(r.get("texto")); // Ajusta según tus columnas
                data.setDate(new Date());
                dataList.add(data);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
