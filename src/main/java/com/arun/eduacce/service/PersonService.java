package com.arun.eduacce.service;

import com.arun.eduacce.Constants.EduacceConstants;
import com.arun.eduacce.model.Person;
import com.arun.eduacce.model.Roles;
import com.arun.eduacce.repository.PersonRepository;
import com.arun.eduacce.repository.RolesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private RolesRepository rolesRepository;

    public boolean createNewPerson(Person person){
        boolean isSaved = false;
        Roles role = rolesRepository.getByRoleName(EduacceConstants.STUDENT_ROLE);
        person.setRoles(role);
        person = personRepository.save(person);
        if (null != person && person.getPersonId() > 0)
        {
            isSaved = true;
        }
        return isSaved;
    }
}
