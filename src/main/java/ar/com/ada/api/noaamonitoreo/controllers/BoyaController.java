package ar.com.ada.api.noaamonitoreo.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ada.api.noaamonitoreo.entities.*;
import ar.com.ada.api.noaamonitoreo.models.request.ActualizarBoyaColor;
import ar.com.ada.api.noaamonitoreo.models.request.InfoBoyaNueva;
import ar.com.ada.api.noaamonitoreo.models.response.BadResponse;
import ar.com.ada.api.noaamonitoreo.models.response.GenericResponse;
import ar.com.ada.api.noaamonitoreo.servicies.BoyaService;

@RestController
public class BoyaController {

    @Autowired
    BoyaService service;

    @Autowired
    BoyaService boyaService;


    @PostMapping("/boyas") // permite la creación boyas
    public ResponseEntity<GenericResponse> crearBoya(@RequestBody InfoBoyaNueva InfoBoyaNueva) {
        Boya boya = service.crearBoya(InfoBoyaNueva.longitudInstalacion, InfoBoyaNueva.latitudInstalacion);

        GenericResponse respuesta = new GenericResponse();

        respuesta.isOk = true;
        respuesta.message = "La boya fue creada con exito.";
        respuesta.id = boya.getBoyaId();

        return ResponseEntity.ok(respuesta);
    }

    @GetMapping("/boyas") // que devuelva las boyas SIN las muestras.
    public ResponseEntity<List<Boya>> traerTodas() {
        return ResponseEntity.ok(service.traerTodas());

    }

    @GetMapping("/boyas/{id}")
    public ResponseEntity<?> buscarBoya(@PathVariable Integer id){
        BadResponse respuesta= new BadResponse();
        if(service.existeBoya(id)){
            Boya boya = service.buscarBoya(id);
            return ResponseEntity.ok(boya);
        }
        else{
            respuesta.isOk=false;
            respuesta.mensaje="El id ingresado no éxiste";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(respuesta);
        }

        
    }

    @PutMapping("/boyas/{id}") // actualice SOLO el color de luz de la boya. El request esperado será:
    public ResponseEntity<GenericResponse> actualizarBoyaColor(@PathVariable Integer id,
            @RequestBody ActualizarBoyaColor colorLuz) {
        GenericResponse respuesta = new GenericResponse();

        if (service.actualizarBoyaColor(id, colorLuz.colorLuz)) {
            respuesta.isOk = true;
            respuesta.message = "Color de boya actualizado con exito.";
            respuesta.id = id;

            return ResponseEntity.ok(respuesta);
        } else {
            respuesta.isOk = false;
            respuesta.message = "El id ingresado no existe.";
            return ResponseEntity.badRequest().body(respuesta);
            
        }
    }
}
