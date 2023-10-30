package com.example.eventOrganizer.ServiceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.EventDAO;
import com.example.eventOrganizer.Entity.EventEntity;
import com.example.eventOrganizer.Service.EventService;

@Service
public class EventServiceImpl implements EventService {
    private static Logger logger = LogManager.getLogger(EventServiceImpl.class);

    @Autowired
    EventDAO EventDAO;

    @Override
    public EventEntity addEventService(EventEntity eventEntity) {
        logger.info("EventServiceImpl :: START :: addEventService() ::");
        return EventDAO.addEvent(eventEntity);
    }

    @Override
    public EventEntity editEventService(EventEntity eventEntity) {
        logger.info("EventServiceImpl :: START :: editEventService() ::");
        return EventDAO.editEvent(eventEntity);
    }

    @Override
    public EventEntity getEventService(Long eventID) {
        logger.info("EventServiceImpl :: START :: getEventService() ::");
        return EventDAO.getEvent(eventID);

    }

    @Override
    public boolean deleteEventService(Long EventID) {
        logger.info("EventServiceImpl :: START :: deleteEventService() ::");
        return EventDAO.deleteEvent(EventID);

    }

    @Override
    public List<EventEntity> getAllEventService(int pageNumber, int pageSize) {
        logger.info("EventServiceImpl :: START :: getAllEventService() ::");
        return EventDAO.getAllEvent(pageNumber, pageSize);
    }

    @Override
    public List<EventEntity> filterEventByAttributesService(EventEntity eventEntity) {
        logger.info("EventServiceImpl :: START :: filterEventByAttributesService() ::");
        return EventDAO.filterEventByAttributesDAO(eventEntity);
    }

    @Override
    public List<EventEntity> getAllEventService() {
        logger.info("EventServiceImpl :: START :: getAllEventService() ::");
        return EventDAO.getAllEvent();
    }

}
