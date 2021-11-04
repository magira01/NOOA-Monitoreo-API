package ar.com.ada.api.noaamonitoreo.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ar.com.ada.api.noaamonitoreo.entities.Muestra;

@Repository
public interface MuestraRepository extends JpaRepository<Muestra, Integer>{

    Muestra findBymuestraId(Integer id);

}
