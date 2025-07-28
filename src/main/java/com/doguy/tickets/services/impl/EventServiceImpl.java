package com.doguy.tickets.services.impl;

import com.doguy.tickets.domain.CreateEventRequest;
import com.doguy.tickets.domain.entities.Event;
import com.doguy.tickets.domain.entities.TicketType;
import com.doguy.tickets.domain.entities.User;
import com.doguy.tickets.exception.UserNotFoundException;
import com.doguy.tickets.repositories.EventRepository;
import com.doguy.tickets.repositories.UserRepository;
import com.doguy.tickets.services.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format("User with that ID is not found")));

        Event eventToCreate = new Event();
        List<TicketType> ticketTypesToCreate = event.getTicketTypes()
                .stream()
                .map(ticketType ->{
                        TicketType ticketTypeToCreate = new TicketType();
                        ticketTypeToCreate.setName(ticketType.getName());
                        ticketTypeToCreate.setPrice(ticketType.getPrice());
                        ticketTypeToCreate.setDescription(ticketType.getDescription());
                        ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                        return  ticketTypeToCreate;
                }).toList();

        eventToCreate.setName(event.getName());
        eventToCreate.setStart(event.getStart());
        eventToCreate.setEnd(event.getEnd());
        eventToCreate.setVenue(event.getVenue());
        eventToCreate.setSalesStart(event.getSalesStart());
        eventToCreate.setSalesEnd(event.getSalesEnd());
        eventToCreate.setStatus(event.getStatus());
        eventToCreate.setOrganizer(organizer);
        eventToCreate.setTicketTypes(ticketTypesToCreate);

        Event save = eventRepository.save(eventToCreate);

        return save;
    }

}
