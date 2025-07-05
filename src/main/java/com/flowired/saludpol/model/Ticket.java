package com.flowired.saludpol.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "tickets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTicket;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "id_tecnico")
    private Tecnico tecnico;

    @ManyToOne
    @JoinColumn(name = "id_sistemas", nullable = false)
    private Sistemas sistemas;

    @ManyToOne
    @JoinColumn(name = "id_plantilla")
    private Plantilla plantilla;

    @Column(name = "dta_celular")
    private String dtaCelular;

    @Column(name = "dta_dni", nullable = false)
    private String dtaDni;

    @Column(name = "dta_nombres")
    private String dtaNombres;

    @Column(name = "tipo_sade")
    private String tipoSede;

    @Column(name = "tipo_alta")
    private String tipoAlta;

    @Column(name = "tareas")
    private String tareas;

    @Column(name = "tipo_estado")
    private String tipoEstado;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Column(name = "fecha_vencimiento")
    private LocalDate fechaVencimiento;
}
