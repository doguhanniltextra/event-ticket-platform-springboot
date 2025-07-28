package com.doguy.tickets.domain.dtos.responses;

import com.doguy.tickets.domain.entities.Ticket;
import com.doguy.tickets.domain.enums.EventStatusEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetEventDetailsResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    private EventStatusEnum status;
    private List<GetEventTicketTypeResponseDto> ticketTypes = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
