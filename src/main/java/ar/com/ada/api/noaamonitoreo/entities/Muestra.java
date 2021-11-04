package ar.com.ada.api.noaamonitoreo.entities;
//Esta posee la información que transmite la boya

import java.util.*;

import javax.persistence.*;

@Entity
@Table(name = "muestra")
public class Muestra {

    public static final Date HorarioMuestra = null;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "muestra_id")
    private Integer muestraId;

    @ManyToOne
    @JoinColumn(name = "boya_id", referencedColumnName = "boya_id")
    private Boya boya;

    @Column(name = "horario_muestra")
    private Date horarioMuestra;

    @Column(name = "matricula_embarcacion")
    private String matriculaEmbarcacion;

    private Double longitud;

    private Double latitud;

    @Column(name = "altura_nivel_mar")
    private Double alturaNivelMar;

    public Integer getMuestraId() {
        return muestraId;
    }

    public void setMuestraId(Integer muestraId) {
        this.muestraId = muestraId;
    }

    public Boya getBoya() {
        return boya;
    }

    public void setBoya(Boya boya) {
        this.boya = boya;
        this.boya.agregarMuestra(this);
    }// Nota 1: una boya genera varias muestras, y una muestra corresponde solo a una
     // boya.

    public Date getHorarioMuestra() {
        return horarioMuestra;
    }

    public void setHorarioMuestra(Date horarioMuestra) {
        this.horarioMuestra = horarioMuestra;
    }

    public String getMatriculaEmbarcacion() {
        return matriculaEmbarcacion;
    }

    public void setMatriculaEmbarcacion(String matriculaEmbarcacion) {
        this.matriculaEmbarcacion = matriculaEmbarcacion;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double i) {
        this.longitud = i;
    }

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getAlturaNivelMar() {
        return alturaNivelMar;
    }

    public void setAlturaNivelMar(Double alturaNivelMar) {
        this.alturaNivelMar = alturaNivelMar;
    }


    /*
     * ROJO: Marea peligrosa
     * AMARILLO: Advertencia de marea peligrosa
     * VERDE: todo Ok
     * AZUL: indefinido*/
     
    public String obtenerColor(Muestra muestra) {

        if (muestra.getAlturaNivelMar() <= -50 && muestra.getAlturaNivelMar() > 50) {
            return "ROJO";
        }
        if (muestra.getAlturaNivelMar() < -100 && muestra.getAlturaNivelMar() > 100) {
            return "AMARILLO";
        } 
        else {
            return "VERDE";
        } 
    }
}



    /*public enum EstadoMareaEnum{

        ROJO(1), //Marea peligrosa
        AMARILLO(2), //Advertencia de marea peligrosa
        VERDE(3),// todo Ok
        AZUL(4);// indefinido
        

        private final int value;

        private EstadoMareaEnum(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static EstadoMareaEnum parse(int id) {
            EstadoMareaEnum status = null; // Default
            for (EstadoMareaEnum item : EstadoMareaEnum.values()) {
                if (item.getValue() == id) {
                    status = item;
                    break;
                }
            }
            return status;
        }   
}


}*/