package com.flowired.saludpol.controller;

import com.flowired.saludpol.model.Plantilla;
import com.flowired.saludpol.repository.PlantillaRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/plantillas")
@Tag(name = "plantillas", description = "Plantillas disponibles")
public class PlantillaController {

    @Autowired
    private PlantillaRepository plantillaRepository;

    @GetMapping
    @Operation(summary = "Listar plantillas disponibles para el usuario")
    public ResponseEntity<List<Plantilla>> listarPlantillas() {
        List<Plantilla> plantillas = plantillaRepository.findAll();
        return ResponseEntity.ok(plantillas);
    }

    @GetMapping("/rol/{idRol}")
    @Operation(summary = "Listar plantillas por ID de rol")
    public ResponseEntity<List<Plantilla>> listarPorRol(@PathVariable Integer idRol) {
        List<Plantilla> plantillas = plantillaRepository.findByRol_IdRol(idRol);
        if (idRol == 1) {
            plantillas = plantillaRepository.findAll();
        } else {
            plantillas = plantillaRepository.findByRol_IdRol(idRol);
        }
        if (plantillas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(plantillas);
    }





}
