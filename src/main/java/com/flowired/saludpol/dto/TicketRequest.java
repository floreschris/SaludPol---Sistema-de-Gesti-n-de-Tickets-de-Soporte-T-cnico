package com.flowired.saludpol.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketRequest {

    private Integer idUsuario;
    private Integer idTecnico;
    private Integer idSistemas;
    private Integer idPlantilla;
    private String dtaCelular;
    private String dtaDni;
    private String dtaNombres;
    private String tipoSede;
    private String tipoAlta;
    private String tareas;
    private String tipoEstado;
    private LocalDate fechaVencimiento;


}
