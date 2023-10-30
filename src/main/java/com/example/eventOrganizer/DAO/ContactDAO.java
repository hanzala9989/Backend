package com.example.eventOrganizer.DAO;

import java.util.List;

import com.example.eventOrganizer.Entity.ContactEntity;

public interface ContactDAO {
    public ContactEntity addContact(ContactEntity contactEntity);

    public ContactEntity editContact(ContactEntity contactEntity);

    public ContactEntity getContact(Long contactID);

    public boolean deleteContact(Long contactID);

    public List<ContactEntity> getAllContact(int pageSize, int pageNumber);

    public List<ContactEntity> filterContactByAttributesDAO(ContactEntity contactEntity);

}
