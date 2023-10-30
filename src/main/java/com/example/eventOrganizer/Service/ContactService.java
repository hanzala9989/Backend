package com.example.eventOrganizer.Service;

import java.util.List;

import com.example.eventOrganizer.Entity.ContactEntity;

public interface ContactService {
    public ContactEntity addContactService(ContactEntity ContactEntity);

    public ContactEntity editContactService(ContactEntity ContactEntity);

    public ContactEntity getContactService(Long contactID);

    public boolean deleteContactService(Long contactID);

    public List<ContactEntity> getAllContactService(int pageNumber, int pageSize);

    public List<ContactEntity> filterContactByAttributesService(ContactEntity contactEntity);

}
