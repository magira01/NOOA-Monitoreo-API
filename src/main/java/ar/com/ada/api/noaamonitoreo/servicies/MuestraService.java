package ar.com.ada.api.noaamonitoreo.servicies;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaamonitoreo.entities.Boya;
import ar.com.ada.api.noaamonitoreo.entities.Muestra;
import ar.com.ada.api.noaamonitoreo.models.response.MuestraPorColor;
import ar.com.ada.api.noaamonitoreo.repos.MuestraRepository;

@Service
public class MuestraService {

    @Autowired
    MuestraRepository repo;

    @Autowired
    BoyaService boyaService;

    public Muestra crearMuestra(Integer boyaId, Date horarioMuestra, String matriculaEmbarcacion, Double latitud,
            Double longitud, Double alturaNivelMar) {
        Muestra muestra = new Muestra();
        Boya boya = boyaService.buscarBoya(boyaId);
        muestra.setBoya(boya);
        muestra.setHorarioMuestra(horarioMuestra);
        muestra.setMatriculaEmbarcacion(matriculaEmbarcacion);
        muestra.setLatitud(latitud);
        muestra.setLongitud(longitud);
        muestra.setAlturaNivelMar(alturaNivelMar);

        repo.save(muestra);
        return muestra;

    }

    public List<Muestra> traerMuestras(Integer idBoya) {
        Boya boya = boyaService.buscarBoya(idBoya);
        if (boya != null) {
            return boya.getMuestras();
        } else
            return null;

    }

    public String colorMuestra(Muestra muestra) {

        if (muestra.getAlturaNivelMar() < -100 || muestra.getAlturaNivelMar() > 100) {
            return "ROJO";
        } else if (muestra.getAlturaNivelMar() < -50 || muestra.getAlturaNivelMar() > 50) {
            return "AMARILLO";
        } else {
            return "VERDE";
        }
    }

    public boolean resetearColorBoya(Integer id) {
        Muestra muestra = repo.findBymuestraId(id);
        if (muestra != null) {
            Boya boya = muestra.getBoya();
            boya.setColorLuz("AZUL");
            boyaService.guardarBoya(boya);
            return true;
        } else
            return false;
    }

    public List<MuestraPorColor> traerMuestrasPorColor(String color) {
        List<MuestraPorColor> muestrasPorColor = new ArrayList<>();
        MuestraPorColor muestraPorColor = new MuestraPorColor();

        for (Muestra muestra : repo.findAll()) {

            if (colorMuestra(muestra).equals(color)) {
                muestraPorColor.boyaId = muestra.getBoya().getBoyaId();
                muestraPorColor.horario = muestra.getHorarioMuestra();
                muestraPorColor.alturaNivelDelMar = muestra.getAlturaNivelMar();

                muestrasPorColor.add(muestraPorColor);
            }
        }
        return muestrasPorColor;

    }

    public Muestra MuestraAlturaMinima(Integer idBoya) {
        Boya boya = boyaService.buscarBoya(idBoya);

        Muestra muestraMinima = boya.getMuestras().get(0);

        for (Muestra muestra : boya.getMuestras()) {
            if (muestra.getAlturaNivelMar() < muestraMinima.getAlturaNivelMar()) {
                muestraMinima = muestra;
            }

        }
        return muestraMinima;
    }

}
