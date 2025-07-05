package com.flowired.saludpol.dto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {

    private String message;
    private String perfil;
    private String rol;
    private String redirectUrl;

}
