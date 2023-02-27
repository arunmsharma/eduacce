package com.arun.eduacce.service;
import com.arun.eduacce.Constants.EduacceConstants;
import com.arun.eduacce.model.Contact;
import com.arun.eduacce.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;


    public ContactService(){
        System.out.println("Contact service bean is initilized");
    }
    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = false;
        contact.setStatus(EduacceConstants.OPEN);
        contact.setCreatedBy(EduacceConstants.ANONYMOUS);
        contact.setCreatedAt(LocalDateTime.now());
        Contact savedContact = contactRepository.save(contact);
        if(savedContact != null && savedContact.getContactId()>0){
            isSaved = true;
        }
        log.info(contact.toString());
        return isSaved;
    }

    public List<Contact> findMsgsWithOpenStatus(){
        List<Contact> contacts = contactRepository.findByStatus(EduacceConstants.OPEN);
        return contacts;
    }

    public boolean updateMsgStatus(int contactId, String updatedBy){
        boolean isUpdated = false;
        Optional<Contact> contact = contactRepository.findById(contactId);
        contact.ifPresent(contact1 ->{
            contact1.setStatus(EduacceConstants.CLOSE);
            contact1.setUpdatedAt(LocalDateTime.now());
            contact1.setUpdatedBy(updatedBy);
        });
        Contact updatedContact = contactRepository.save(contact.get());
        if(updatedContact!=null && updatedContact.getContactId()>0){
            isUpdated = true;
        }
        return isUpdated;
    }


}
