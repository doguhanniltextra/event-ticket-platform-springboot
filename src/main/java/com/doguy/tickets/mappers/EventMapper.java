package com.doguy.tickets.mappers;

import com.doguy.tickets.domain.CreateEventRequest;
import com.doguy.tickets.domain.CreateTicketTypeRequest;
import com.doguy.tickets.domain.dtos.CreateEventRequestDto;
import com.doguy.tickets.domain.dtos.CreateEventResponseDto;
import com.doguy.tickets.domain.dtos.ListEventResponseDto;
import com.doguy.tickets.domain.dtos.ListEventTicketTypeResponseDto;
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

    ListEventResponseDto toListEventResponseDto(Event event);
}
