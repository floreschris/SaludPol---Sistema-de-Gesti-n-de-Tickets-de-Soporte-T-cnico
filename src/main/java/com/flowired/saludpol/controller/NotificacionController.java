package com.flowired.saludpol.controller;

import com.flowired.saludpol.dto.NotificacionCorreoRequest;
import com.flowired.saludpol.model.Ticket;
import com.flowired.saludpol.model.Usuario;
import com.flowired.saludpol.repository.TicketRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/notificaciones")
@Tag(name = "notificaciones", description = "Envío de notificaciones")
public class NotificacionController {

    @Autowired
    private TicketRepository ticketRepository;

    @PostMapping("/tickets/{id}/correo")
    @Operation(summary = "Enviar notificación por correo al usuario del ticket")
    public ResponseEntity<String> enviarCorreoNotificacion(
            @PathVariable Integer id,
            @RequestBody NotificacionCorreoRequest request) {

        Optional<Ticket> ticketOpt = ticketRepository.findById(id);
        if (ticketOpt.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ticket no encontrado");
        }

        Ticket ticket = ticketOpt.get();

        // Usamos el correo que viene en el request
        String correo = request.getCorreoDestino();
        if (correo == null || correo.isBlank()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Correo destino no válido");
        }

        // Simulación del envío de correo
        System.out.printf("""
        Enviando correo a: %s
        Nombre del usuario: %s
        Asunto: %s
        Mensaje: %s
        """,
                correo,
                request.getNombreUsuario(),
                request.getAsunto(),
                request.getMensaje()
        );

        return ResponseEntity.ok("Correo enviado exitosamente");
    }

}
