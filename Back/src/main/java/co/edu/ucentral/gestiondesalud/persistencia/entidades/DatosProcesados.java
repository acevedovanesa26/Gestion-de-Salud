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
    private String tema;

    // Campos nuevos para el análisis de sentimiento
    private String sentimiento;
    private int score;

    // --- Getters y Setters ---

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextoOriginal() {
        return textoOriginal;
    }

    public void setTextoOriginal(String textoOriginal) {
        this.textoOriginal = textoOriginal;
    }

    public String getTextoProcesado() {
        return textoProcesado;
    }

    public void setTextoProcesado(String textoProcesado) {
        this.textoProcesado = textoProcesado;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getSentimiento() {
        return sentimiento;
    }

    public void setSentimiento(String sentimiento) {
        this.sentimiento = sentimiento;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
