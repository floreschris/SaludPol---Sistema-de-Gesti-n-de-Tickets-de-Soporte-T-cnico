package com.flowired.saludpol.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantilla {

    private Integer idPlantilla;
    private Integer idTipoPlantilla;
    private String prioridad;
    private String asunto;
    private String descripcion;
    private String archivosAdjuntos;
}
