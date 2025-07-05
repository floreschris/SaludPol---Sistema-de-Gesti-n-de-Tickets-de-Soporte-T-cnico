package com.flowired.saludpol.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "tecnicos")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tecnico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTecnico;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Role rol;

    @ManyToOne
    @JoinColumn(name = "id_grupo")
    private Grupo grupo;

    private String nombre;

}
