package ar.com.ada.api.challenge1.entities;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "muestra")
public class Muestra {
    
    @Column(name = "muestra_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer muestraId;
    
    @Column(name = "horario_muestra")
    private Date horarioMuestra;
    
    @Column(name = "matricula_embarcacion")
    private String matriculaEmbarcacion;
   
    private Double latitud;
    
    private Double longitud;
    
    @Column(name = "altura_nivel_mar")
    private Double alturaNivelMar;
    @ManyToOne
    @JoinColumn(name = "boya_id", referencedColumnName = "boya_id")
    private Boya boya;

    public Muestra() {
    }

    public Muestra(Boya boya, Double alturaNivelMar, Date horario, Double latitud, Double longitud, String matricula) {
        this.boya = boya;
        this.alturaNivelMar = alturaNivelMar;
        this.horarioMuestra = horario;
        this.latitud = latitud;
        this.longitud = longitud;
        this.matriculaEmbarcacion = matricula;
    }

    public Integer getMuestraId() {
        return muestraId;
    }

    public void setMuestraId(Integer muestraId) {
        this.muestraId = muestraId;
    }

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

    public Double getLatitud() {
        return latitud;
    }

    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    public Double getLongitud() {
        return longitud;
    }

    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    public Double getAlturaNivelMar() {
        return alturaNivelMar;
    }

    public void setAlturaNivelMar(Double alturaNivelMar) {
        this.alturaNivelMar = alturaNivelMar;
    }

    public Boya getBoya() {
        return boya;
    }

    public void setBoya(Boya boya) {
        this.boya = boya;
        this.boya.getMuestras().add(this);
    }

}