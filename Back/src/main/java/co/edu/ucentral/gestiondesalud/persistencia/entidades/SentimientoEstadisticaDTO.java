// SentimientoEstadisticaDTO.java
package co.edu.ucentral.gestiondesalud.persistencia.entidades;

public class SentimientoEstadisticaDTO {
    private long positivos;
    private long negativos;
    private long neutros;
    private long total;


    public SentimientoEstadisticaDTO(long positivos, long negativos, long neutros) {
        this.positivos = positivos;
        this.negativos = negativos;
        this.neutros = neutros;
        this.total = positivos + negativos + neutros;
    }

    public long getPositivos() {
        return positivos;
    }

    public void setPositivos(long positivos) {
        this.positivos = positivos;
    }

    public long getNegativos() {
        return negativos;
    }

    public void setNegativos(long negativos) {
        this.negativos = negativos;
    }

    public long getNeutros() {
        return neutros;
    }

    public void setNeutros(long neutros) {
        this.neutros = neutros;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }


}
