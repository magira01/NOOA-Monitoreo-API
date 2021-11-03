package ar.com.ada.api.challenge1.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestBody;

import ar.com.ada.api.challenge1.entities.*;
import ar.com.ada.api.challenge1.models.request.ActualizarBoyaColor;
import ar.com.ada.api.challenge1.models.request.InfoBoyaNueva;
import ar.com.ada.api.challenge1.models.response.GenericResponse;
import ar.com.ada.api.challenge1.servicies.BoyaService;

@RestController
public class BoyaController {

    @Autowired
    BoyaService service;

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

    @GetMapping("/boyas/{id}") // que devuelva la info de una boya en particular(SIN las muestras)
    public ResponseEntity<?> traerById(@PathVariable Integer boyaId) {
        if (service.traerById(boyaId) == null) {

            GenericResponse respuesta = new GenericResponse();
            respuesta.isOk = false;
            respuesta.message = "El id ingresado no corresponde a ninguna boya.";

            return ResponseEntity.badRequest().body(respuesta);

        } else {

            return ResponseEntity.ok(service.traerById(boyaId));

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
