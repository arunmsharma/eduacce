package com.arun.eduacce.repository;

import com.arun.eduacce.model.Holiday;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HolidayRepository extends CrudRepository<Holiday,String> {

}
