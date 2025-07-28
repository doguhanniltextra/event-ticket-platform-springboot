package com.doguy.tickets.domain.dtos.requests;

import com.doguy.tickets.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.sql.Update;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateEventRequestDto {
    @NotNull(message = "Event ID is required")
    private UUID id;
    @NotBlank(message = "Event name is required")
    private String name;
    private LocalDateTime start;
    private LocalDateTime end;
    @NotBlank(message = "Venue info are required")
    private String venue;
    private LocalDateTime salesStart;
    private LocalDateTime salesEnd;
    @NotNull(message = "Event status is required")
    private EventStatusEnum status;
    @NotEmpty(message = "At least one ticket type is required")
    @Valid
    private List<UpdateTicketTypeRequestDto> ticketTypes;
}
