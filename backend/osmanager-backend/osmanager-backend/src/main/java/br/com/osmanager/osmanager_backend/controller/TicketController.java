package br.com.osmanager.osmanager_backend.controller;

import br.com.osmanager.osmanager_backend.entity.Ticket;
import br.com.osmanager.osmanager_backend.service.TicketService;
import br.com.osmanager.osmanager_backend.service.dto.CreateTicketRequest;
import br.com.osmanager.osmanager_backend.service.dto.TicketHistoryResponse;
import br.com.osmanager.osmanager_backend.service.dto.TicketResponse;
import br.com.osmanager.osmanager_backend.service.dto.UpdateTicketStatusRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    public ResponseEntity<TicketResponse> createTicket(
            @Valid @RequestBody CreateTicketRequest request,
            Authentication authentication) {

        Ticket ticket = ticketService.createTicket(
                request,
                authentication
        );

        TicketResponse response = new TicketResponse(ticket);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<TicketResponse>> findMyTickets(
            Authentication authentication) {

        List<Ticket> tickets =
                ticketService.findMyTickets(authentication);

        List<TicketResponse> response = tickets.stream()
                .map(TicketResponse::new)
                .toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{protocol}")
    public ResponseEntity<TicketResponse> findByProtocol(
            @PathVariable String protocol,
            Authentication authentication) {

        Ticket ticket =
                ticketService.findByProtocol(protocol, authentication);

        List<TicketHistoryResponse> history =
                ticketService.findHistory(ticket.getId())
                        .stream()
                        .map(TicketHistoryResponse::new)
                        .toList();

        TicketResponse response =
                new TicketResponse(ticket, history);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{protocol}/assign")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public ResponseEntity<TicketResponse> assignTicket(
            @PathVariable String protocol,
            Authentication authentication) {

        Ticket ticket =
                ticketService.assignTicket(protocol, authentication);

        return ResponseEntity.ok(new TicketResponse(ticket));
    }

    @PatchMapping("/{protocol}/status")
    @PreAuthorize("hasRole('TECHNICIAN')")
    public ResponseEntity<TicketResponse> updateStatus(
            @PathVariable String protocol,
            @Valid @RequestBody UpdateTicketStatusRequest request,
            Authentication authentication) {

        Ticket ticket = ticketService.updateStatus(
                protocol,
                request.getStatus(),
                authentication
        );

        return ResponseEntity.ok(new TicketResponse(ticket));
    }
}
