package com.flowired.saludpol.model;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "sistemas")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Sistemas {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idSistemas;

    private String nombre;

}
