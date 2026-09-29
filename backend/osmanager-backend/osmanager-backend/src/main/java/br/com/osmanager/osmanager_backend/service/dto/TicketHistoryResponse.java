package br.com.osmanager.osmanager_backend.service.dto;

import br.com.osmanager.osmanager_backend.entity.TicketHistory;
import br.com.osmanager.osmanager_backend.entity.TicketStatus;

import java.time.LocalDateTime;

public class TicketHistoryResponse {

    private TicketStatus oldStatus;
    private TicketStatus newStatus;
    private String description;
    private String userName;
    private LocalDateTime createdAt;

    public TicketHistoryResponse(TicketHistory history) {
        this.oldStatus = history.getOldStatus();
        this.newStatus = history.getNewStatus();
        this.description = history.getDescription();
        this.userName = history.getUser().getName();
        this.createdAt = history.getCreatedAt();
    }

    public TicketStatus getOldStatus() {
        return oldStatus;
    }

    public TicketStatus getNewStatus() {
        return newStatus;
    }

    public String getDescription() {
        return description;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}