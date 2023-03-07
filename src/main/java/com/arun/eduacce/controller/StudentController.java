package com.arun.eduacce.controller;

import com.arun.eduacce.model.Person;
import com.arun.eduacce.repository.CoursesRepository;
import com.arun.eduacce.repository.EduacceClassRepository;
import com.arun.eduacce.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@Slf4j
@RequestMapping("student")
public class StudentController {

    final EduacceClassRepository eduacceClassRepository;

    final PersonRepository personRepository;

    final CoursesRepository coursesRepository;

    @Autowired
    public StudentController(EduacceClassRepository eduacceClassRepository, PersonRepository personRepository, CoursesRepository coursesRepository) {
        this.eduacceClassRepository = eduacceClassRepository;
        this.personRepository = personRepository;
        this.coursesRepository = coursesRepository;
    }

    @RequestMapping("/displayCourses")
    public ModelAndView displayCourses(Model model, HttpSession session){
        ModelAndView modelAndView = new ModelAndView("courses_enrolled");
        Person student = (Person) session.getAttribute("loggedInPerson");
        modelAndView.addObject("person",student);
        return modelAndView;
    }
}

