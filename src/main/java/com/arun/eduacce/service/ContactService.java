package com.arun.eduacce.service;

import com.arun.eduacce.Constants.EduacceConstants;
import com.arun.eduacce.model.Contact;
import com.arun.eduacce.repository.ContactRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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
        Contact savedContact = contactRepository.save(contact);
        if(savedContact != null && savedContact.getContactId()>0){
            isSaved = true;
        }
        log.info(contact.toString());
        return isSaved;
    }

    public Page<Contact> findMsgsWithOpenStatus(int pageNum, String sortField, String sortDir){
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize,
                sortDir.equals("asc") ? Sort.by(sortField).ascending()
                        : Sort.by(sortField).descending());
        Page<Contact> msgPage = contactRepository.findByStatus(
                EduacceConstants.OPEN,pageable);
        return msgPage;
    }


    public boolean updateMsgStatus(int contactId){
        boolean isUpdated = false;
        int rows = contactRepository.updateStatusById(EduacceConstants.CLOSE,contactId);
        if(rows > 0){
            isUpdated = true;
        }
        return isUpdated;
    }

}
