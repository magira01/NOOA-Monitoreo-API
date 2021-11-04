package ar.com.ada.api.noaamonitoreo.repos;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaamonitoreo.entities.Boya;



@Repository
public interface BoyaRepository extends JpaRepository<Boya, Integer> {

   
    Boya findByboyaId(Integer id);

    List<Boya> findByColorLuz(String colorLuz);

    
}
