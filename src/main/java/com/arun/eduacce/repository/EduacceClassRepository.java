package com.arun.eduacce.repository;

import com.arun.eduacce.model.EduacceClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EduacceClassRepository extends JpaRepository<EduacceClass,Integer> {
}
