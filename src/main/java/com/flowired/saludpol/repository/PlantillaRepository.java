package com.flowired.saludpol.repository;

import com.flowired.saludpol.model.Plantilla;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlantillaRepository extends JpaRepository<Plantilla, Integer> {
    List<Plantilla> findByRol_IdRol(Integer idRol);

}
