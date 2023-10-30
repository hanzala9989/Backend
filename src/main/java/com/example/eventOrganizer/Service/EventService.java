package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.EventEntity;

public interface EventService {
    public EventEntity addEventService(EventEntity eventEntity);

    public EventEntity editEventService(EventEntity eventEntity);

    public EventEntity getEventService(Long eventID);

    public boolean deleteEventService(Long eventID);

    public List<EventEntity> getAllEventService(int pageNumber, int pageSize);
    
    public List<EventEntity> getAllEventService();

    public List<EventEntity> filterEventByAttributesService(EventEntity eventEntity);
}
