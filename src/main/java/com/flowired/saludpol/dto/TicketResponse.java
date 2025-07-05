package com.flowired.saludpol.dto;

import java.time.LocalDateTime;
import java.util.Map;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketResponse {

    private Integer idTicket;
    private String tipoEstado;
    private LocalDateTime fechaCreacion;
    private String fechaVencimiento;
    private String mensaje;
    private Map<String, String> estadoTareas;

}
