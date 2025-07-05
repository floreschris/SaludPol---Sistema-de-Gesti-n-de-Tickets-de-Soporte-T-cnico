package com.flowired.saludpol.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AsignacionTecnicoResponse {

    private Integer idTicket;
    private Integer idTecnico;
    private String nombreTecnico;
    private String mensaje;

}
