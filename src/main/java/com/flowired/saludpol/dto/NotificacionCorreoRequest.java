package com.flowired.saludpol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificacionCorreoRequest {

    private String asunto;
    private String mensaje;
    private String correoDestino;
    private String nombreUsuario;

}
