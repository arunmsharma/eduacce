package com.arun.eduacce.repository;


import com.arun.eduacce.model.Contact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
@Repository stereotype annotation is used to add a bean of this class
type to the Spring context and indicate that given Bean is used to perform
DB related operations and
* */
@Repository
public interface ContactRepository extends JpaRepository<Contact,Integer> {
    List<Contact> findByStatus(String status);

    @Query("select c from Contact c where c.status = :status")
//    @Query(value = "SELECT * FROM contact_msg C WHERE C.status = :status",nativeQuery = true)
    Page<Contact> findByStatus(String status, Pageable pageable);


    @Transactional
    @Modifying
    @Query("UPDATE Contact c SET c.status = ?1 where c.contactId=?2")
    int updateStatusById(String status,int id);
}
