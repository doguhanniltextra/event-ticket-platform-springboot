package com.doguy.tickets.domain.dtos;

import com.doguy.tickets.domain.enums.EventStatusEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateEventRequestDto {
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
    private List<CreateTicketTypeRequestDto> ticketTypes;
}
