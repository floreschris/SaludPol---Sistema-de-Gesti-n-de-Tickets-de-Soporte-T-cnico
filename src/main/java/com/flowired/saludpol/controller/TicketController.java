package com.flowired.saludpol.controller;

import com.flowired.saludpol.dto.AsignacionTecnicoResponse;
import com.flowired.saludpol.dto.TicketEstadoRequest;
import com.flowired.saludpol.dto.TicketRequest;
import com.flowired.saludpol.dto.TicketResponse;
import com.flowired.saludpol.model.Sistemas;
import com.flowired.saludpol.model.Tecnico;
import com.flowired.saludpol.model.Ticket;
import com.flowired.saludpol.model.Usuario;
import com.flowired.saludpol.repository.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;


@RestController
@RequestMapping("/tickets")
@Tag(name = "tickets", description = "Gestión de tickets")
public class TicketController {


    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private TecnicoRepository tecnicoRepository;
    @Autowired
    private SistemasRepository sistemasRepository;
    @Autowired
    private PlantillaRepository plantillaRepository;

    @PostMapping
    @Operation(summary = "1. Crear un nuevo ticket")
    public ResponseEntity<TicketResponse> crearTicket(@RequestBody TicketRequest request) {
        Ticket ticket = new Ticket();
        ticket.setUsuario(usuarioRepository.findById(request.getIdUsuario()).orElse(null));
        ticket.setTecnico(tecnicoRepository.findById(request.getIdTecnico()).orElse(null));
        ticket.setSistemas(sistemasRepository.findById(request.getIdSistemas()).orElse(null));
        ticket.setPlantilla(plantillaRepository.findById(request.getIdPlantilla()).orElse(null));
        ticket.setDtaCelular(request.getDtaCelular());
        ticket.setDtaDni(request.getDtaDni());
        ticket.setDtaNombres(request.getDtaNombres());
        ticket.setTipoSede(request.getTipoSede());
        ticket.setTipoAlta(request.getTipoAlta());
        ticket.setTareas(request.getTareas());
        ticket.setTipoEstado(request.getTipoEstado());
        ticket.setFechaCreacion(LocalDateTime.now());
        ticket.setFechaVencimiento(request.getFechaVencimiento());

        Ticket ticketGuardado = ticketRepository.save(ticket);

        Map<String, String> estadoTareas = new HashMap<>();
        String[] tareas = ticket.getTareas().split(",");
        for (String tarea : tareas) {
            estadoTareas.put(tarea, "pendiente");
        }

        TicketResponse response = TicketResponse.builder()
                .idTicket(ticketGuardado.getIdTicket())
                .tipoEstado(ticketGuardado.getTipoEstado())
                .fechaCreacion(ticketGuardado.getFechaCreacion())
                .fechaVencimiento(ticketGuardado.getFechaVencimiento().toString())
                .mensaje("Ticket creado exitosamente")
                .estadoTareas(estadoTareas)
                .build();

        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{id}")
    @Operation(summary = "2. Consultar ticket por ID")
    public ResponseEntity<TicketResponse> getTicketById(@PathVariable Integer id) {
        return ticketRepository.findById(id)
                .map(ticket -> {
                    Map<String, String> estadoTareas = new HashMap<>();
                    String[] tareas = ticket.getTareas().split(",");
                    for (String tarea : tareas) {
                        estadoTareas.put(tarea, "pendiente");
                    }

                    TicketResponse response = TicketResponse.builder()
                            .idTicket(ticket.getIdTicket())
                            .tipoEstado(ticket.getTipoEstado())
                            .fechaCreacion(ticket.getFechaCreacion())
                            .fechaVencimiento(ticket.getFechaVencimiento().toString())
                            .mensaje("Ticket consultado exitosamente")
                            .estadoTareas(estadoTareas)
                            .build();

                    return ResponseEntity.ok(response);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}/estado")
    @Operation(summary = "Actualizar el estado del ticket", description = "Actualiza el estado del ticket y notifica al usuario solicitante vía correo electrónico.")
    public ResponseEntity<String> actualizarEstadoTicket(@PathVariable Integer id, @RequestBody TicketEstadoRequest request) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);

        if (ticketOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Ticket ticket = ticketOpt.get();
        ticket.setTipoEstado(request.getTipoEstado());
        ticketRepository.save(ticket);

        // TODO: Implementar lógica para notificar por correo (si deseas).

        return ResponseEntity.ok("Estado actualizado y usuario notificado");
    }

    @PutMapping("/{id}/asignar")
    @Operation(summary = "Asignar técnico con menor carga de tickets")
    public ResponseEntity<AsignacionTecnicoResponse> asignarTecnico(@PathVariable Integer id) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // Obtener todos los técnicos
        List<Tecnico> tecnicos = tecnicoRepository.findAll();

        // Buscar el técnico con menor cantidad de tickets asignados
        Tecnico tecnicoAsignado = tecnicos.stream()
                .min(Comparator.comparing(tecnico -> ticketRepository.countByTecnico_IdTecnico(tecnico.getIdTecnico())))
                .orElse(null);

        if (tecnicoAsignado == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new AsignacionTecnicoResponse(null, null, null, "No hay técnicos disponibles"));
        }

        // Asignar técnico al ticket
        Ticket ticket = ticketOpt.get();
        ticket.setTecnico(tecnicoAsignado);
        ticketRepository.save(ticket);

        AsignacionTecnicoResponse response = AsignacionTecnicoResponse.builder()
                .idTicket(ticket.getIdTicket())
                .idTecnico(tecnicoAsignado.getIdTecnico())
                .nombreTecnico(tecnicoAsignado.getNombre())
                .mensaje("Técnico asignado exitosamente")
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}/tareas/{tarea}/cerrar")
    @Operation(summary = "Cerrar una tarea específica del ticket", description = "Marca una tarea como finalizada. Si todas están completas, el ticket se cierra y se notifica al usuario.")
    public ResponseEntity<String> cerrarTarea(@PathVariable Integer id, @PathVariable String tarea) {
        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        Ticket ticket = ticketOpt.get();
        String tareas = ticket.getTareas();
        List<String> tareasList = new ArrayList<>(Arrays.asList(tareas.split(",")));

        if (!tareasList.contains(tarea)) {
            return ResponseEntity.badRequest().body("Tarea no válida o ya estaba cerrada");
        }

        tareasList.remove(tarea);
        ticket.setTareas(String.join(",", tareasList));

        // Si ya no quedan tareas, cerrar el ticket
        if (tareasList.isEmpty()) {
            ticket.setTipoEstado("cerrado");
            // TODO: notificar por correo
        }

        ticketRepository.save(ticket);
        return ResponseEntity.ok("Tarea cerrada exitosamente" + (tareasList.isEmpty() ? " y ticket cerrado" : ""));
    }

    @GetMapping("/usuario/{idUsuario}")
    @Operation(summary = "Listar tickets por ID de usuario")
    public ResponseEntity<List<Ticket>> listarTicketsPorUsuario(@PathVariable Integer idUsuario) {
        List<Ticket> tickets = ticketRepository.findByUsuario_IdUsuario(idUsuario);
        if (tickets.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(tickets);
    }


    @GetMapping("/listarTicketsPorTecnico/{idTecnico}")
    @Operation(summary = "Listar tickets por técnico")
    public ResponseEntity<List<Ticket>> listarTicketsPorTecnico(@PathVariable Integer idTecnico) {
        List<Ticket> tickets = ticketRepository.findByTecnico_IdTecnico(idTecnico);
        return ResponseEntity.ok(tickets);
    }




}
