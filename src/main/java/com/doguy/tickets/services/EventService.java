package com.doguy.tickets.services;

import com.doguy.tickets.domain.CreateEventRequest;
import com.doguy.tickets.domain.entities.Event;

import java.util.UUID;

public interface EventService {
    Event createEvent(UUID organizerId, CreateEventRequest event);
}
