package ar.com.ada.api.noaamonitoreo.entities;

import java.util.*;
import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "boya")
public class Boya {
    @Id
    @Column(name = "boya_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer boyaId;
    @Column(name = "color_luz")
    private String colorLuz = "AZUL";
    @Column(name = "latitud_instalacion")
    private double latitudInstalacion;
    @Column(name = "longitud_instalacion")
    private double longitudInstalacion;
    @OneToMany(mappedBy = "boya", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @LazyCollection(LazyCollectionOption.FALSE)
    @JsonIgnore
    private List<Muestra> muestras = new ArrayList<>();

    

    public Integer getBoyaId() {
        return boyaId;
    }

    public void setBoyaId(Integer boyaId) {
        this.boyaId = boyaId;
    }

    public double getLatitudInstalacion() {
        return latitudInstalacion;
    }

    public void setLatitudInstalacion(double latitudInstalacion) {
        this.latitudInstalacion = latitudInstalacion;
    }

    public double getLongitudInstalacion() {
        return longitudInstalacion;
    }

    public void setLongitudInstalacion(double longitudInstalacion) {
        this.longitudInstalacion = longitudInstalacion;
    }

    public List<Muestra> getMuestras() {
        return muestras;
    }

    public void setMuestras(List<Muestra> muestras) {
        this.muestras = muestras;
    }

    public String getColorLuz() {
        return colorLuz;
    }

    public void setColorLuz(String colorLuz) {
        this.colorLuz = colorLuz;
    }

    public void agregarMuestra(Muestra muestra) {
    }

   
    

    
}