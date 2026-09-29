package br.com.osmanager.osmanager_backend.repository;

import br.com.osmanager.osmanager_backend.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

    Optional<Ticket> findByProtocol(String protocol);

    boolean existsByProtocol(String protocol);

    List<Ticket> findByRequesterIdOrderByCreatedAtDesc(Long requesterId);

}