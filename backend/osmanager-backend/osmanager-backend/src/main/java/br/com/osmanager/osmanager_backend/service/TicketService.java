package br.com.osmanager.osmanager_backend.service;

import br.com.osmanager.osmanager_backend.entity.*;
import br.com.osmanager.osmanager_backend.exception.BusinessRuleException;
import br.com.osmanager.osmanager_backend.exception.ResourceNotFoundException;
import br.com.osmanager.osmanager_backend.repository.AuditLogRepository;
import br.com.osmanager.osmanager_backend.repository.TicketHistoryRepository;
import br.com.osmanager.osmanager_backend.repository.TicketRepository;
import br.com.osmanager.osmanager_backend.repository.UserRepository;
import br.com.osmanager.osmanager_backend.service.dto.CreateTicketRequest;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;
    private final TicketHistoryRepository ticketHistoryRepository;
    private final AuditLogRepository auditLogRepository;

    public TicketService(
            TicketRepository ticketRepository,
            UserRepository userRepository,
            TicketHistoryRepository ticketHistoryRepository,
            AuditLogRepository auditLogRepository) {

        this.ticketRepository = ticketRepository;
        this.userRepository = userRepository;
        this.ticketHistoryRepository = ticketHistoryRepository;
        this.auditLogRepository = auditLogRepository;
    }

    public Ticket createTicket(
            CreateTicketRequest request,
            Authentication authentication) {

        String email = authentication.getName();

        User requester = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário autenticado não encontrado"));

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setCategory(request.getCategory());
        ticket.setPriority(request.getPriority());

        ticket.setStatus(TicketStatus.ABERTO);
        ticket.setRequester(requester);

        ticket.setProtocol(generateProtocol());

        ticket.setCreatedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketHistory history = new TicketHistory();

        history.setTicket(savedTicket);
        history.setUser(requester);
        history.setOldStatus(null);
        history.setNewStatus(TicketStatus.ABERTO);
        history.setDescription("Chamado criado");
        history.setCreatedAt(LocalDateTime.now());

        ticketHistoryRepository.save(history);

        AuditLog auditLog = new AuditLog();

        auditLog.setUser(requester);
        auditLog.setAction("CREATE");
        auditLog.setEntity("Ticket");
        auditLog.setEntityId(savedTicket.getId());
        auditLog.setDescription(
                "Chamado " + savedTicket.getProtocol() + " criado"
        );
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        return savedTicket;

    }

    private String generateProtocol() {

        String protocol;

        do {
            long number = System.currentTimeMillis();

            protocol = "TCK-" + LocalDateTime.now().getYear()
                    + "-" + number;

        } while (ticketRepository.existsByProtocol(protocol));

        return protocol;
    }


    public List<Ticket> findMyTickets(Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Usuário autenticado não encontrado"));

        return ticketRepository
                .findByRequesterIdOrderByCreatedAtDesc(user.getId());
    }

    public Ticket findByProtocol(
            String protocol,
            Authentication authentication) {

        String email = authentication.getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Usuário autenticado não encontrado"
                        ));

        Ticket ticket = ticketRepository.findByProtocol(protocol)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Chamado não encontrado"
                        ));

        boolean isOwner =
                ticket.getRequester().getId().equals(user.getId());

        boolean isTechnicianOrAdmin =
                user.getRole().getName().equals("TECHNICIAN")
                        || user.getRole().getName().equals("ADMIN");

        if (!isOwner && !isTechnicianOrAdmin) {
            throw new AccessDeniedException(
                    "Você não tem permissão para acessar este chamado"
            );
        }

        return ticket;
    }

    public List<TicketHistory> findHistory(Long ticketId) {
        return ticketHistoryRepository
                .findByTicketIdOrderByCreatedAtAsc(ticketId);
    }

    public Ticket assignTicket(
            String protocol,
            Authentication authentication) {

        String email = authentication.getName();

        User technician = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Técnico autenticado não encontrado"
                        ));

        Ticket ticket = ticketRepository.findByProtocol(protocol)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Chamado não encontrado"
                        ));

        if (!ticket.getStatus().equals(TicketStatus.ABERTO)) {
            throw new IllegalStateException(
                    "Somente chamados com status ABERTO podem ser assumidos"
            );
        }

        ticket.setTechnician(technician);
        ticket.setStatus(TicketStatus.EM_ATENDIMENTO);
        ticket.setUpdatedAt(LocalDateTime.now());

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketHistory history = new TicketHistory();
        history.setTicket(savedTicket);
        history.setUser(technician);
        history.setOldStatus(TicketStatus.ABERTO);
        history.setNewStatus(TicketStatus.EM_ATENDIMENTO);
        history.setDescription("Chamado assumido pelo técnico");
        history.setCreatedAt(LocalDateTime.now());

        ticketHistoryRepository.save(history);

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(technician);
        auditLog.setAction("ASSIGN");
        auditLog.setEntity("Ticket");
        auditLog.setEntityId(savedTicket.getId());
        auditLog.setDescription(
                "Chamado " + savedTicket.getProtocol()
                        + " assumido pelo técnico"
        );
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        return savedTicket;
    }

    private void validateStatusTransition(
            TicketStatus currentStatus,
            TicketStatus newStatus) {

        boolean valid = switch (currentStatus) {

            case EM_ATENDIMENTO ->
                    newStatus == TicketStatus.AGUARDANDO_USUARIO
                            || newStatus == TicketStatus.AGUARDANDO_TERCEIRO
                            || newStatus == TicketStatus.RESOLVIDO;

            case AGUARDANDO_USUARIO,
                 AGUARDANDO_TERCEIRO ->
                    newStatus == TicketStatus.EM_ATENDIMENTO;

            default -> false;
        };

        if (!valid) {
            throw new BusinessRuleException(
                    "Transição de status inválida: "
                            + currentStatus
                            + " → "
                            + newStatus
            );
        }
    }

    public Ticket updateStatus(
            String protocol,
            TicketStatus newStatus,
            Authentication authentication) {

        String email = authentication.getName();

        User technician = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Técnico autenticado não encontrado"
                        ));

        Ticket ticket = ticketRepository.findByProtocol(protocol)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Chamado não encontrado"
                        ));

        if (ticket.getTechnician() == null ||
                !ticket.getTechnician().getId().equals(technician.getId())) {

            throw new AccessDeniedException(
                    "Este chamado não está atribuído ao técnico"
            );
        }

        TicketStatus oldStatus = ticket.getStatus();

        validateStatusTransition(oldStatus, newStatus);

        ticket.setStatus(newStatus);
        ticket.setUpdatedAt(LocalDateTime.now());

        if (newStatus == TicketStatus.RESOLVIDO) {
            ticket.setResolvedAt(LocalDateTime.now());
        }

        Ticket savedTicket = ticketRepository.save(ticket);

        TicketHistory history = new TicketHistory();
        history.setTicket(savedTicket);
        history.setUser(technician);
        history.setOldStatus(oldStatus);
        history.setNewStatus(newStatus);
        history.setDescription("Status do chamado alterado");
        history.setCreatedAt(LocalDateTime.now());

        ticketHistoryRepository.save(history);

        AuditLog auditLog = new AuditLog();
        auditLog.setUser(technician);
        auditLog.setAction("UPDATE_STATUS");
        auditLog.setEntity("Ticket");
        auditLog.setEntityId(savedTicket.getId());
        auditLog.setDescription(
                "Status do chamado " +
                        savedTicket.getProtocol() +
                        " alterado de " +
                        oldStatus +
                        " para " +
                        newStatus
        );
        auditLog.setCreatedAt(LocalDateTime.now());

        auditLogRepository.save(auditLog);

        return savedTicket;
    }
}