package com.example.eventOrganizer.DAO;

import java.util.List;

import com.example.eventOrganizer.Entity.EventEntity;

public interface EventDAO {
    public EventEntity addEvent(EventEntity EventEntity);

    public EventEntity editEvent(EventEntity EventEntity);

    public EventEntity getEvent(Long EventID);

    public boolean deleteEvent(Long EventID);

    public List<EventEntity> getAllEvent(int pageSize, int pageNumber);

    public List<EventEntity> getAllEvent();

    public List<EventEntity> filterEventByAttributesDAO(EventEntity EventEntity);
}
