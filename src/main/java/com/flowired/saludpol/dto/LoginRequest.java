package com.flowired.saludpol.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {

    private String idUsuario;
    private String password;

}
