package br.com.osmanager.osmanager_backend.service.dto;

import br.com.osmanager.osmanager_backend.entity.Ticket;
import br.com.osmanager.osmanager_backend.entity.TicketCategory;
import br.com.osmanager.osmanager_backend.entity.TicketPriority;
import br.com.osmanager.osmanager_backend.entity.TicketStatus;

import java.time.LocalDateTime;
import java.util.List;

public class TicketResponse {

    private Long id;
    private String protocol;
    private String title;
    private String description;
    private TicketCategory category;
    private TicketPriority priority;
    private TicketStatus status;
    private String requesterName;
    private LocalDateTime createdAt;
    private List<TicketHistoryResponse> history;

    public TicketResponse(Ticket ticket) {
        this.id = ticket.getId();
        this.protocol = ticket.getProtocol();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.category = ticket.getCategory();
        this.priority = ticket.getPriority();
        this.status = ticket.getStatus();
        this.requesterName = ticket.getRequester().getName();
        this.createdAt = ticket.getCreatedAt();
    }


    public TicketResponse(Ticket ticket, List<TicketHistoryResponse> history) {
        this.id = ticket.getId();
        this.protocol = ticket.getProtocol();
        this.title = ticket.getTitle();
        this.description = ticket.getDescription();
        this.category = ticket.getCategory();
        this.priority = ticket.getPriority();
        this.status = ticket.getStatus();
        this.requesterName = ticket.getRequester().getName();
        this.createdAt = ticket.getCreatedAt();
        this.history = history;
    }



    public Long getId() {
        return id;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public String getRequesterName() {
        return requesterName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public List<TicketHistoryResponse> getHistory() {
        return history;
    }
}
