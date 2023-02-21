package com.arun.eduacce.service;
import com.arun.eduacce.model.Contact;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

@Service
@Slf4j
//@RequestScope
//@SessionScope
@ApplicationScope
public class ContactService {


    /**
     * Save Contact Details into DB
     * @param contact
     * @return boolean
     */

    private int counter = 0;
    public ContactService(){
        System.out.println("Contact service bean is initilized");
    }
    public boolean saveMessageDetails(Contact contact){
        boolean isSaved = true;
        //TODO - Need to persist the data into the DB table
        log.info(contact.toString());
        return isSaved;
    }

    public int getCounter() {
        return counter;
    }

    public void setCounter(int counter) {
        this.counter = counter;
    }
}
