package ar.com.ada.api.noaamonitoreo.servicies;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ar.com.ada.api.noaamonitoreo.entities.Boya;
import ar.com.ada.api.noaamonitoreo.repos.BoyaRepository;



@Service
public class BoyaService {
    
    @Autowired
    BoyaRepository repo;
    
    @Autowired
    BoyaRepository repoBoya;

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

    public Boya buscarPorId(Integer id) {
        Optional<Boya> opBoya = repoBoya.findById(id);

        if (opBoya.isPresent())
            return opBoya.get();
        else
            return null;
    }

    public List<Boya> traerTodas(){
        return repo.findAll();
    }

    

    public List<Boya> traerByColor(String color) {
        return repo.findByColorLuz(color);
    }

    public boolean actualizarBoyaColor(Integer id, String colorLuz) {

        Boya boya = buscarPorId(id);
        if(boya!=null){
            boya.setColorLuz(colorLuz);
            guardarBoya(boya);
            return true;
        }
        return false;
    }
    public Boya buscarBoya(Integer boyaId) {
        return repo.findByboyaId(boyaId);
    }
    
    public boolean existeBoya(Integer boyaId){
        if(buscarBoya(boyaId)!=null)
            return true;
        else
            return false;
    }

}
