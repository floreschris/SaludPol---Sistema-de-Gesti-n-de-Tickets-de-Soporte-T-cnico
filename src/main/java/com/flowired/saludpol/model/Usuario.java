package com.flowired.saludpol.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuario")
@Getter
@Setter
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_rol")
    private Role rol;

    @ManyToOne
    @JoinColumn(name = "id_area")
    private Area area;

    @Column(name = "password")
    private String password;


    private String nombres;
    private String apellidos;
    private String correo;
    private String sede;

}
