package com.flowired.saludpol.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "plantilla")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Plantilla {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idPlantilla;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = true)
    private Role rol;


    @Column(name = "id_tipo_plantilla")
    private Integer idTipoPlantilla;

    private String prioridad;
    private String asunto;
    private String descripcion;

    @Column(name = "archivos_adjuntos")
    private String archivosAdjuntos;

}
