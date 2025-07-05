package com.flowired.saludpol.repository;

import com.flowired.saludpol.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    List<Ticket> findByUsuario_IdUsuario(Integer idUsuario);
    List<Ticket> findByTecnico_IdTecnico(Integer idTecnico);
    long countByTecnico_IdTecnico(Integer idTecnico);

}
