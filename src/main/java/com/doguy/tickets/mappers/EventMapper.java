package com.doguy.tickets.mappers;

import com.doguy.tickets.domain.CreateEventRequest;
import com.doguy.tickets.domain.CreateTicketTypeRequest;
import com.doguy.tickets.domain.dtos.requests.CreateEventRequestDto;
import com.doguy.tickets.domain.dtos.responses.*;
import com.doguy.tickets.domain.entities.Event;
import com.doguy.tickets.domain.entities.TicketType;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface EventMapper {
    CreateTicketTypeRequest fromDto(CreateTicketTypeRequest dto);
    CreateEventRequest fromDto(CreateEventRequestDto dto);
    CreateEventResponseDto toDto(Event event);

    ListEventTicketTypeResponseDto toDto(TicketType ticketType);

    GetEventTicketTypeResponseDto toGetEventTicketTypeResponseDto(TicketType ticketType);
    GetEventDetailsResponseDto toGetEventDetailsResponseDto(Event event);

    ListEventResponseDto toListEventResponseDto(Event event);
}
