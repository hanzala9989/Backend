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

import com.example.eventOrganizer.Entity.ContactEntity;
import com.example.eventOrganizer.Service.ContactService;
import com.example.eventOrganizer.Uitility.ResponseHandler;

@RestController
@CrossOrigin("*")
@ResponseBody
@RequestMapping("/api/contact")
public class ContactController {
    private static Logger logger = LogManager.getLogger(ContactController.class);

    @Autowired
    ContactService contactService;

    @PostMapping("/createContact")
    public ResponseEntity<ResponseHandler> AddContact(@RequestBody ContactEntity contactEntity) {
        logger.info("ContactController :: START :: AddContact() ::");
        try {
            if (contactEntity != null) {
                contactService.addContactService(contactEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Contact Add Successfully"));
            }
            return null;
        } catch (Exception ex) {
            logger.error("Exception in ContactController :: FAILED :: AddContact() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Add Contact :: " + ex.getMessage()));
        }
    }

    @PutMapping("/editContact")
    public ResponseEntity<ResponseHandler> EditContact(@RequestBody ContactEntity contactEntity) {
        logger.info("ContactController :: START :: EditContact() ::");
        try {
            if (contactEntity != null) {
                contactService.editContactService(contactEntity);
                return ResponseEntity.ok(new ResponseHandler("200", "Help History Updated Successfully"));
            }
            throw new NullPointerException("ContactEntity is NULL Exception");
        } catch (Exception ex) {
            logger.error("Exception in ContactController :: FAILED :: EditContact() :: Internal Server Error ");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Error Updated Contact :: " + ex.getMessage()));
        }
    }

    @GetMapping("/getAllContact")
    public ResponseEntity<ResponseHandler> GetAllContact(
            @RequestParam(name = "pageNumber", defaultValue = "1") int pageNumber,
            @RequestParam(name = "pageSize", defaultValue = "10") int pageSize) {
        logger.info("ContactController :: START :: GetAllContact() ::");
        try {
            logger.info("ContactController :: PROCESS :: GetAllContact() :: Success");
            List<ContactEntity> contactList = contactService.getAllContactService(pageNumber, pageSize);
            return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(contactList)));
        } catch (Exception ex) {
            logger.error("Exception in ContactController :: FAILED :: GetAllContact() :: Data Not Found", ex);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Data Not Found", List.of()));
        }
    }

    @GetMapping("/getContactByID")
    public ResponseEntity<ResponseHandler> getContactByID(@RequestParam Long contactID) {
        try {
            logger.info("ContactController :: START :: getContactByID() ::");
            ContactEntity contactObject = contactService.getContactService(contactID);
            if (contactObject != null) {
                logger.info("ContactController:: PROCESS :: getContactByID() :: Contact SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(contactObject)));
            } else {
                logger.error("Exception in ContactController :: FAILED :: getContactByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
            }
        } catch (Exception e) {
            logger.error("Exception in ContactController :: FAILED :: getContactByID() :: Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @DeleteMapping("/deleteContactByID")
    public ResponseEntity<ResponseHandler> deleteContactByID(@RequestParam Long contactID) {
        logger.info("ContactController :: START :: deleteContactByID() ::");
        try {
            boolean contact = contactService.deleteContactService(contactID);

            if (contact) {
                logger.info("ContactController:: PROCESS :: deleteContactByID() :: Contact Deleted SuccessFully");
                return ResponseEntity.ok(new ResponseHandler("200", "Contact Deleted SuccessFully"));
            } else {
                logger.error("Exception in ContactController :: FAILED :: deleteContactByID() :: Data not found");
                return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
            }
        } catch (EntityNotFoundException ex) {
            logger.error("Exception in ContactController :: FAILED :: deleteContactByID() :: Data not found :: ", ex);
            return ResponseEntity.ok(new ResponseHandler("500", "Data not found", List.of()));
        } catch (Exception e) {
            logger.error("Exception in ContactController :: FAILED :: deleteContactByID() :: Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseHandler("500", "Internal Server Error", List.of()));
        }
    }

    @PostMapping("/filter")
    public ResponseEntity<ResponseHandler> filterContact(@RequestBody ContactEntity filterDTO) {
        try {
            logger.info("RewardController :: PROCESS :: filterReward() :: Success");
            List<ContactEntity> contactList = contactService.filterContactByAttributesService(filterDTO);
            if (contactList.size() > 0) {
                return ResponseEntity.ok(new ResponseHandler("200", "Success", List.of(contactList)));
            } else {
                return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of(contactList)));
            }
        } catch (Exception ex) {
            logger.error("Exception in RewardController :: FAILED :: filterReward() :: Data Not Found");
            return ResponseEntity.ok(new ResponseHandler("500", "Data Not Found", List.of()));
        }
    }
}
