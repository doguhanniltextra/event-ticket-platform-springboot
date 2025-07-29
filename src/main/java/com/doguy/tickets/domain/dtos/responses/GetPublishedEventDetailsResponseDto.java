package com.doguy.tickets.domain.dtos.responses;

import com.doguy.tickets.domain.entities.Ticket;
import com.doguy.tickets.domain.enums.EventStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GetPublishedEventDetailsResponseDto {
    private UUID id;
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    private String venue;
    private List<GetPublishedEventTicketTypeResponseDto> ticketTypes = new ArrayList<>();
    private List<Ticket> tickets = new ArrayList<>();
}
