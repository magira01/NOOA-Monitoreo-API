package ar.com.ada.api.challenge1.servicies;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.challenge1.entities.Boya;
import ar.com.ada.api.challenge1.repos.BoyaRepository;



@Service
public class BoyaService {
    
    @Autowired
    BoyaRepository repo;

    public Boya crearBoya(double longitudInstalacion, double latitudInstacion) {
        Boya boya = new Boya(); 
        boya.setLatitudInstalacion(latitudInstacion);
        boya.setLongitudInstalacion(longitudInstalacion);

        guardarBoya(boya);
        return boya;

    }
    
    public void guardarBoya(Boya boya) {
        repo.save(boya);
    }

    public List<Boya> traerTodas(){
        return repo.findAll();
    }

    public Boya traerById(Integer boyaId){
        return repo.findByBoyaId(boyaId);
    }

    public List<Boya> traerByColor(String color) {
        return repo.findByColorLuz(color);
    }

    public boolean actualizarBoyaColor(Integer id, String colorLuz) {

        Boya boya = traerById(id);
        if(boya!=null){
            boya.setColorLuz(colorLuz);
            guardarBoya(boya);
            return true;
        }
        return false;
    }

}
