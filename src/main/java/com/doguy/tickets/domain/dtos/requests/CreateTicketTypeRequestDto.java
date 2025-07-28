package com.doguy.tickets.domain.dtos.requests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateTicketTypeRequestDto {

    @NotBlank(message = "Ticket type name is required")
    private String name;

    @NotNull(message = "Price cannot be null")
    @PositiveOrZero(message = "Price mut be zero or greater")
    private Double price;

    // OPTIONAL
    private String description;

    private Integer totalAvailable;
}
