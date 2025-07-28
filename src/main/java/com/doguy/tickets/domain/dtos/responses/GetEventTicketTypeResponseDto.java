package com.doguy.tickets.domain.dtos.responses;
import com.doguy.tickets.domain.entities.Ticket;
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
public class GetEventTicketTypeResponseDto {
    private UUID id;
    private String name;
    private Double price;
    private Integer totalAvailable;
    private String description;
    private List<Ticket> tickets = new ArrayList<>();
    private LocalDateTime createdAt;
}
