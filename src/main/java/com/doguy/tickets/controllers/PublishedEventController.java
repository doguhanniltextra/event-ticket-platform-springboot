package com.doguy.tickets.controllers;

import com.doguy.tickets.domain.dtos.responses.GetPublishedEventDetailsResponseDto;
import com.doguy.tickets.domain.dtos.responses.ListEventPublishedResponseDto;
import com.doguy.tickets.domain.entities.Event;
import com.doguy.tickets.mappers.EventMapper;
import com.doguy.tickets.services.EventService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "/api/v1/published-events")
@RequiredArgsConstructor
public class PublishedEventController {

    private final EventService eventService;
    private final EventMapper eventMapper;

    @GetMapping
    public ResponseEntity<Page<ListEventPublishedResponseDto>> listPublishedEvents(@RequestParam(required = false) String q, Pageable pageable) {

        Page<Event> events;

        if(null != q && !q.trim().isEmpty()) {
            events = eventService.searchPublishedEvents(q, pageable);
        } else {
            events = eventService.listPublishedEvents(pageable);
        }


        return ResponseEntity.ok(events
                .map(eventMapper::toListEventPublishedResponseDto));
    }

    @GetMapping(path = "/{eventId}")
    public ResponseEntity<GetPublishedEventDetailsResponseDto> getPublishedEventsDetails(
            @PathVariable UUID eventId) {
            return eventService.getPublishedEvent(eventId)
                    .map(eventMapper::toGetPublishedEventDetailsResponseDto)
                    .map(ResponseEntity::ok)
                    .orElse(ResponseEntity.notFound().build());
    }
}
