// DatosProcesados.java
package co.edu.ucentral.gestiondesalud.persistencia.entidades;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "datos_procesados")
public class DatosProcesados {

    @Id
    private String id;

    private String textoOriginal;
    private String textoProcesado;
    private Date fecha;

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTextoProcesado() {
        return textoProcesado;
    }

    public void setTextoProcesado(String textoProcesado) {
        this.textoProcesado = textoProcesado;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
// Getters y setters
}
