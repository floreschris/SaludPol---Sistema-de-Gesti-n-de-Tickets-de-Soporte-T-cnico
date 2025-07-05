package com.flowired.saludpol.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfile {

    private String idUsuario;
    private String nombres;
    private String apellidos;
    private String correo;
    private String telefono;
    private String area;
    private String oficina;
    private String rol;
    private List<String> gruposAD;

}
