package com.doguy.tickets.services.impl;

import com.doguy.tickets.domain.CreateEventRequest;
import com.doguy.tickets.domain.UpdateEventRequest;
import com.doguy.tickets.domain.UpdateTicketTypeRequest;
import com.doguy.tickets.domain.entities.Event;
import com.doguy.tickets.domain.entities.TicketType;
import com.doguy.tickets.domain.entities.User;
import com.doguy.tickets.exception.EventNotFoundException;
import com.doguy.tickets.exception.EventUpdateException;
import com.doguy.tickets.exception.TicketTypeotFoundException;
import com.doguy.tickets.exception.UserNotFoundException;
import com.doguy.tickets.repositories.EventRepository;
import com.doguy.tickets.repositories.UserRepository;
import com.doguy.tickets.services.EventService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final UserRepository userRepository;
    private final EventRepository eventRepository;

    @Override
    @Transactional
    public Event createEvent(UUID organizerId, CreateEventRequest event) {
        User organizer = userRepository.findById(organizerId)
                .orElseThrow(
                        () -> new UserNotFoundException(
                                String.format("User with that ID is not found")));

        Event eventToCreate = new Event();
        List<TicketType> ticketTypesToCreate = event
                .getTicketTypes()
                .stream()
                .map(ticketType ->{
                        TicketType ticketTypeToCreate = new TicketType();
                        ticketTypeToCreate.setName(ticketType.getName());
                        ticketTypeToCreate.setPrice(ticketType.getPrice());
                        ticketTypeToCreate.setDescription(ticketType.getDescription());
                        ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                        ticketTypeToCreate.setEvent(eventToCreate);
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

    @Override
    public Page<Event> listEventsForOrganizer(UUID organizerId, Pageable pageable) {
        return eventRepository.findByOrganizerId(organizerId, pageable);
    }

    @Override
    public Optional<Event> getEventForOrganizer(UUID organizerId, UUID id) {
        return eventRepository.findByIdAndOrganizerId(id, organizerId);
    }

    @Override
    @Transactional
    public Event updateEventForOrganizer(UUID organizerId, UUID id, UpdateEventRequest event) {
       if(null == event.getId()) {
           throw new EventUpdateException("Event ID cannot be null");
       }

       if(!id.equals(event.getId())) {
           throw new EventUpdateException("Provided IDs do not match");
       }

        Event existingEvent = eventRepository
                .findByIdAndOrganizerId(id, organizerId)
                .orElseThrow(() -> new EventNotFoundException(
                        String.format("Event with that ID doesn't exit")));


        existingEvent.setName(event.getName());
        existingEvent.setStart(event.getStart());
        existingEvent.setEnd(event.getEnd());
        existingEvent.setVenue(event.getVenue());
        existingEvent.setSalesEnd(event.getSalesEnd());
        existingEvent.setSalesStart(event.getSalesStart());
        existingEvent.setStatus(event.getStatus());


        Set<UUID> requestTicketTypeIds = event.getTicketTypes()
                .stream()
                .map(UpdateTicketTypeRequest::getId)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        existingEvent.getTicketTypes().removeIf(existingTicketType ->
                !requestTicketTypeIds.contains(existingTicketType.getId()));

        Map<UUID, TicketType> existingTicketTypesIndex = existingEvent.getTicketTypes().stream()
                .collect(Collectors.toMap(TicketType::getId, Function.identity()));

        for(UpdateTicketTypeRequest ticketType : event.getTicketTypes()) {
            if(null == ticketType.getId()) {
                TicketType ticketTypeToCreate = new TicketType();
                ticketTypeToCreate.setName(ticketType.getName());
                ticketTypeToCreate.setPrice(ticketType.getPrice());
                ticketTypeToCreate.setDescription(ticketType.getDescription());
                ticketTypeToCreate.setTotalAvailable(ticketType.getTotalAvailable());
                ticketTypeToCreate.setEvent(existingEvent);
                existingEvent.getTicketTypes().add(ticketTypeToCreate);


            } else if (existingTicketTypesIndex.containsKey(ticketType.getId())) {

                TicketType existingTicketType = existingTicketTypesIndex.get(ticketType.getId());
                existingTicketType.setName(ticketType.getName());
                existingTicketType.setPrice(ticketType.getPrice());
                existingTicketType.setDescription(ticketType.getDescription());
                existingTicketType.setTotalAvailable(ticketType.getTotalAvailable());

            } else {
                throw new TicketTypeotFoundException(String.format(
                        "Ticket type with that ID does not exist"
                ));
            }
        }

        return eventRepository.save(existingEvent);
    }




}
