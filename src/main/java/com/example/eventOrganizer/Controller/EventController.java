package com.example.eventOrganizer.Controller;

import java.util.List;

import javax.persistence.EntityNotFoundException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.eventOrganizer.Entity.EventEntity;
import com.example.eventOrganizer.Service.EventService;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@ResponseBody
@RequestMapping("/api/event")
@CrossOrigin("*")
public class EventController {
    private static Logger logger = LogManager.getLogger(EventController.class);

    @Autowired
    EventService eventService;

    @PostMapping("/createEvent")
    public ResponseEntity<ResponseHandler> addEvent(@RequestBody EventEntity eventEntity) {
        logger.info("EventController :: START :: addEvent() ::");
        try {
            if (eventEntity != null) {
                eventService.addEventService(eventEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Event Added Successfully"));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in EventController :: FAILED :: AddEvent() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Add Event :: " + ex.getMessage()));
        }
    }

    @PutMapping("/editEvent")
    public ResponseEntity<ResponseHandler> EditEvent(@RequestBody EventEntity eventEntity) {
        logger.info("EventController :: START :: EditEvent() ::");
        try {
            if (eventEntity != null) {
                eventService.editEventService(eventEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Event Updated Successfully"));
            }
            throw new NullPointerException("EventEntity is NULL Exception");
        } catch (Exception ex) {
            logger.error("Exception in EventController :: FAILED :: EditEvent() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Updated Event :: " + ex.getMessage()));
        }
    }

    @GetMapping("/getAllEvent")
    public ResponseEntity<ResponseHandler> GetAllEvent(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("EventController :: START :: GetAllEvent() ::");
        try {
            logger.info("EventController :: PROCESS :: GetAllEvent() :: Success");
            List<EventEntity> eventList = eventService.getAllEventService(pageNumber, pageSize);
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(eventList)));
        } catch (Exception ex) {
            logger.error("Exception in EventController :: FAILED :: GetAllEvent() :: Data Not Found", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Data Not Found", List.of()));
        }
    }

    @GetMapping("/getEventByID")
    public ResponseEntity<ResponseHandler> getEventByID(@RequestParam Long eventID) {
        try {
            logger.info("EventController :: START :: getEventByID() ::");
            EventEntity eventObject = eventService.getEventService(eventID);
            if (eventObject != null) {
                logger.info("EventController:: PROCESS :: getEventByID() :: event SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(eventObject)));
            } else {
                logger.error("Exception in EventController :: FAILED :: getEventByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
            }
        } catch (Exception e) {
            logger.error("Exception in EventController :: FAILED :: getEventByID() :: Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @DeleteMapping("/deleteEventByID")
    public ResponseEntity<ResponseHandler> deleteEventByID(@RequestParam Long eventID) {
        logger.info("EventController :: START :: deleteEventByID() ::");
        try {
            boolean Event = eventService.deleteEventService(eventID);

            if (Event) {
                logger.info("EventController:: PROCESS :: deleteEventByID() :: Event Deleted SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Event Deleted SuccessFully"));
            } else {
                logger.error("Exception in EventController :: FAILED :: deleteEventByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
            }
        } catch (EntityNotFoundException ex) {
            logger.error("Exception in EventController :: FAILED :: deleteEventByID() :: Data not found :: ", ex);
            return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
        } catch (Exception e) {
            logger.error("Exception in EventController :: FAILED :: deleteEventByID() :: Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseHandler> filterEvent(@RequestBody EventEntity filterDTO) {
        try {
            logger.info("RewardController :: PROCESS :: filterReward() :: Success");
            List<EventEntity> eventList = eventService.filterEventByAttributesService(filterDTO);
            if (eventList.size() > 0) {
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(eventList)));
            } else {
                return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of(eventList)));
            }
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: filterReward() :: Data Not Found");
            return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of()));
        }
    }

    public List<EventEntity> GetAllEvents(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("EventController :: START :: GetAllEvent() ::");
        try {
            logger.info("EventController :: PROCESS :: GetAllEvent() :: Success");
            List<EventEntity> eventList = eventService.getAllEventService(pageNumber, pageSize);
            return eventList;
        } catch (Exception ex) {
            logger.error("Exception in EventController :: FAILED :: GetAllEvent() :: Data Not Found", ex);
            return List.of();
        }
    }
}
