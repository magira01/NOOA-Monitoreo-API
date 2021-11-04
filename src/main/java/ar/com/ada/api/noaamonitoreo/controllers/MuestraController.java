package ar.com.ada.api.noaamonitoreo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ar.com.ada.api.noaamonitoreo.entities.Boya;
import ar.com.ada.api.noaamonitoreo.entities.Muestra;
import ar.com.ada.api.noaamonitoreo.models.request.InfoMuestraNueva;
import ar.com.ada.api.noaamonitoreo.models.response.GenericResponse;
import ar.com.ada.api.noaamonitoreo.models.response.MuestraMinimaResponse;
import ar.com.ada.api.noaamonitoreo.models.response.MuestraPorColor;
import ar.com.ada.api.noaamonitoreo.servicies.BoyaService;
import ar.com.ada.api.noaamonitoreo.servicies.MuestraService;

@RestController
public class MuestraController {

    @Autowired
    MuestraService service;

    @Autowired
    BoyaService boyaService;

    @PostMapping("/muestras") // que registre una muestra
    public ResponseEntity<MuestraMinimaResponse> crearMuestra(@RequestBody InfoMuestraNueva InfoMuestra) {
        MuestraMinimaResponse respuestaMuestra = new MuestraMinimaResponse();

        Muestra muestra = service.crearMuestra(InfoMuestra.boyaId, InfoMuestra.horarioMuestra,
                InfoMuestra.matriculaEmbarcacion, InfoMuestra.latitud, InfoMuestra.longitud,
                InfoMuestra.alturaNivelMar);

        respuestaMuestra.id = muestra.getMuestraId();
        respuestaMuestra.color = service.colorMuestra(muestra);

        return ResponseEntity.ok(respuestaMuestra);
    }

    @GetMapping("/muestras/boyas/{idBoya}")
    public ResponseEntity<?> traerMuestras(@PathVariable Integer idBoya){
        GenericResponse respuesta= new GenericResponse();
        if(service.traerMuestras(idBoya)!=null){
            return ResponseEntity.ok(service.traerMuestras(idBoya));
        }
        else{
            respuesta.isOk=false;
            respuesta.message="No existe id ingresado";
            return ResponseEntity.badRequest().body(respuesta);

        }

    }

    // Reseteara el color de la boya a “AZUL” a partir de una muestra
    @DeleteMapping("/muestras/{id}")
    public ResponseEntity<GenericResponse> resetearColorBoya(@PathVariable Integer id) {
        GenericResponse respuesta = new GenericResponse();

        if (service.resetearColorBoya(id)) {
            respuesta.isOk = true;
            respuesta.id = id;
            respuesta.message = "Color de boya reseteado con éxito.";

            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.isOk = false;
            respuesta.id = id;
            respuesta.message = "El id de muestra no exite.";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }
    }

    // BONUS1/2
    /*
     * ue devuelva la lista de muestras de un color en el siguiente formato JSON
     * Array")
     */
    @GetMapping("/muestras/colores/{color}")
    public ResponseEntity<List<MuestraPorColor>> muestraColor(@PathVariable String color) {

        return ResponseEntity.ok(service.traerMuestrasPorColor(color.toUpperCase()));
    }

    /*
     * : que devuelva la muestra donde la altura nivel del mar sea la minima para
     * una boya en particular en este formato JSON(informar el horario en que
     * ocurrió)
     */
    @GetMapping("/muestras/minima/{idBoya}")
    public ResponseEntity<MuestraMinimaResponse> muestraAlturaMinima(@PathVariable Integer idBoya) {
        Muestra muestraMinima = service.MuestraAlturaMinima(idBoya);

        MuestraMinimaResponse muestraMinimaResponse = new MuestraMinimaResponse();

        muestraMinimaResponse.alturaNivelDelMarMinima = muestraMinima.getAlturaNivelMar();
        muestraMinimaResponse.color = muestraMinima.getBoya().getColorLuz();
        muestraMinimaResponse.horario = muestraMinima.getHorarioMuestra();

        return ResponseEntity.ok(muestraMinimaResponse);
    }

    // EPICBONUS
    /*
     * se ha comenzado a hacer un plan de contingencia para los casos que seamos
     * atacados por Monstruos Gigantes o Naves Alienígenas marítimas. Lo que NOAA
     * está pidiendo es armar un sistema de alerta que nos avise si una anomalía ha
     * sucedido con las boyas. Este sistema deberá devolver un resultado diferente
     * dependiendo de las siguientes consignas: A) Para una boya en particular, si
     * se mantuvo en un lapso de 10 minutos a niveles de 200metros absolutos(o sea
     * -200 o +200) por 10 minutos, ALERTA DE KAIJU (Monstruo Gigante como GODZILLA)
     * B) Para una boya en particular, si se tuvo 2 muestras seguidas donde la
     * diferencia de altura entre ambas es de +500 : ALERTA DE IMPACTO (Posible
     * meteorito o Nave Alienígena que da brincos en el agua)
     */
    // @GetMapping("api/muestras/anomalias/{idBoya}")

}
