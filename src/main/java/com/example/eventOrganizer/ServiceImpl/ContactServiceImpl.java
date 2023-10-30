package com.example.eventOrganizer.ServiceImpl;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventOrganizer.DAO.ContactDAO;
import com.example.eventOrganizer.Entity.ContactEntity;
import com.example.eventOrganizer.Service.ContactService;

@Service
public class ContactServiceImpl implements ContactService {

    private static Logger logger = LogManager.getLogger(ContactServiceImpl.class);

    @Autowired
    ContactDAO contactDAO;

    @Override
    public ContactEntity addContactService(ContactEntity ContactEntity) {
        logger.info("ContactServiceImpl :: START :: addContactService() ::");
        return contactDAO.addContact(ContactEntity);
    }

    @Override
    public ContactEntity editContactService(ContactEntity ContactEntity) {
        logger.info("ContactServiceImpl :: START :: editContactService() ::");
        return contactDAO.editContact(ContactEntity);
    }

    @Override
    public ContactEntity getContactService(Long contactID) {
        logger.info("ContactServiceImpl :: START :: getContactService() ::");
        return contactDAO.getContact(contactID);
    }

    @Override
    public boolean deleteContactService(Long contactID) {
        logger.info("ContactServiceImpl :: START :: deleteContactService() ::");
        return contactDAO.deleteContact(contactID);
    }

    @Override
    public List<ContactEntity> getAllContactService(int pageNumber, int pageSize) {
        logger.info("ContactServiceImpl :: START :: getAllContactService() ::");
        return contactDAO.getAllContact(pageNumber, pageSize);
    }

    @Override
    public List<ContactEntity> filterContactByAttributesService(ContactEntity contactEntity) {
        logger.info("ContactServiceImpl :: START :: filterContactByAttributesService() ::");
        return contactDAO.filterContactByAttributesDAO(contactEntity);
    }

}
