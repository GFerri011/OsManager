package br.com.osmanager.osmanager_backend.service.dto;

import br.com.osmanager.osmanager_backend.entity.TicketCategory;
import br.com.osmanager.osmanager_backend.entity.TicketPriority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;


public class CreateTicketRequest {

    @NotBlank(message = "O título é obrigatório")
    @Size(max = 150, message = "O título deve ter no máximo 150 caracteres")
    private String title;

    @NotBlank(message = "A descrição é obrigatória")
    @Size(min = 10, max = 5000,
            message = "A descrição deve ter entre 10 e 5000 caracteres")
    private String description;

    @NotNull(message = "A categoria é obrigatória")
    private TicketCategory category;

    @NotNull(message = "A prioridade é obrigatória")
    private TicketPriority priority;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public TicketCategory getCategory() {
        return category;
    }

    public void setCategory(TicketCategory category) {
        this.category = category;
    }

    public TicketPriority getPriority() {
        return priority;
    }

    public void setPriority(TicketPriority priority) {
        this.priority = priority;
    }
}
