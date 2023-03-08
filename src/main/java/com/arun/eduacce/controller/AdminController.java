package com.arun.eduacce.controller;

import com.arun.eduacce.model.Courses;
import com.arun.eduacce.model.EduacceClass;
import com.arun.eduacce.model.Person;
import com.arun.eduacce.repository.CoursesRepository;
import com.arun.eduacce.repository.EduacceClassRepository;
import com.arun.eduacce.repository.PersonRepository;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    EduacceClassRepository eduacceClassRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    CoursesRepository coursesRepository;

    @RequestMapping("/displayClasses")
    public ModelAndView displayClasses(Model model){
        List<EduacceClass> eduacceClassList = eduacceClassRepository.findAll();
        ModelAndView modelAndView = new ModelAndView("classes.html");
        modelAndView.addObject("eduacceClass",new EduacceClass());
        modelAndView.addObject("eduacceClasses",eduacceClassList);
        return modelAndView;
    }

    @PostMapping("/addNewClass")
    public ModelAndView addNewClass(Model model, @ModelAttribute("eduacceClass") EduacceClass eduacceClass){
        eduacceClassRepository.save(eduacceClass);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/deleteClass")
    public ModelAndView deleteClass(Model model, @RequestParam Integer id){
        Optional<EduacceClass> eduacceClass = eduacceClassRepository.findById(id);
        for(Person person : eduacceClass.get().getPersons()){
            person.setEduacceClass(null);
            personRepository.save(person);
        }
        eduacceClassRepository.deleteById(id);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayClasses");
        return modelAndView;
    }

    @RequestMapping("/displayStudents")
    public ModelAndView displayStudents(Model model, @RequestParam("classId") Integer id, HttpSession session,
                                        @RequestParam(value="error",required = false) String error){

        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("students.html");
        Optional<EduacceClass> eduacceClass = eduacceClassRepository.findById(id);
        session.setAttribute("eduacceClass",eduacceClass.get());
        modelAndView.addObject("eduacceClass",eduacceClass.get());
        modelAndView.addObject("person",new Person());
        if(error!=null){
            errorMessage = "Invalid email entered!!";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }
    @RequestMapping("/addStudent")
    public ModelAndView addStudent(Model model, @ModelAttribute("person") Person person,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        EduacceClass eduacceClass = (EduacceClass) session.getAttribute("eduacceClass");
        Person person1 = personRepository.readByEmail(person.getEmail());
        if(person1 == null || !(person1.getPersonId()>0)){
            modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eduacceClass.getClassId()
            +"&error=true");
            return modelAndView;
        }
        person1.setEduacceClass(eduacceClass);
        personRepository.save(person1);
        eduacceClass.getPersons().add(person1);
        eduacceClassRepository.save(eduacceClass);
        modelAndView.setViewName("redirect:/admin/displayStudents?classId="+eduacceClass.getClassId());
        return modelAndView;
    }
    @RequestMapping("/deleteStudent")
    public ModelAndView deleteStudent(Model model,@RequestParam("personId") Integer id, HttpSession session){
        EduacceClass eduacceClass = (EduacceClass) session.getAttribute("eduacceClass");
        Optional<Person> person = personRepository.findById(id);
        person.get().setEduacceClass(null);
        eduacceClass.getPersons().remove(person.get());
        EduacceClass eduacceClassSaved = eduacceClassRepository.save(eduacceClass);
        personRepository.save(person.get());
        session.setAttribute("eduacceClass",eduacceClassSaved);
        ModelAndView modelAndView = new ModelAndView("redirect:/admin/displayStudents?classId="+eduacceClass.getClassId());
        return modelAndView;
    }

    @GetMapping("/displayCourses")
    public ModelAndView displayCourses(Model model){
        List<Courses> courses = coursesRepository.findByOrderByNameDesc();
        ModelAndView modelAndView = new ModelAndView("courses_secure.html");
        modelAndView.addObject("courses",courses);
        modelAndView.addObject("course",new Courses());
        return modelAndView;
    }

    @PostMapping("/addNewCourse")
    public ModelAndView addNewCourse(Model model, @ModelAttribute("course") Courses course){
        ModelAndView modelAndView = new ModelAndView();
        coursesRepository.save(course);
        modelAndView.setViewName("redirect:/admin/displayCourses");
        return modelAndView;
    }

    @GetMapping("/viewStudents")
    public ModelAndView viewStudents(Model model, @RequestParam("id") Integer id, HttpSession session,
                                     @RequestParam(value = "error",required = false) String error){
        String errorMessage = null;
        ModelAndView modelAndView = new ModelAndView("course_students.html");
        Optional<Courses> course = coursesRepository.findById(id);
        modelAndView.addObject("courses",course.get());
        modelAndView.addObject("person",new Person());
        session.setAttribute("courseId",id);
        if(error!=null){
            errorMessage = "Invalid email entered!!";
            modelAndView.addObject("errorMessage",errorMessage);
        }
        return modelAndView;
    }

    @PostMapping("/addStudentToCourse")
    public ModelAndView addStudentToCourse(Model model, @ModelAttribute("person") Person person,HttpSession session){
        ModelAndView modelAndView = new ModelAndView();
        Integer courseId = (Integer) session.getAttribute("courseId");
        Optional<Courses> course = coursesRepository.findById(courseId);
        Person studentEntity = personRepository.readByEmail(person.getEmail());
        if(studentEntity==null || studentEntity.getPersonId()<0){
            //error
            modelAndView.setViewName("redirect:/admin/viewStudents?id="+courseId+"&error=true");
            return modelAndView;
        }
        assert studentEntity != null;
        studentEntity.getCourses().add(course.get());
        course.get().getPersons().add(studentEntity);
        personRepository.save(studentEntity);
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courseId);
        return modelAndView;
    }

    @RequestMapping("/deleteStudentFromCourse")
    public ModelAndView deleteStudentFromCourse(Model model,@RequestParam("personId") Integer studentId,
                                                HttpSession session){
        Integer courseId = (Integer) session.getAttribute("courseId");
        Optional<Person> studentEntity = personRepository.findById(studentId);
        ModelAndView modelAndView = new ModelAndView();
        if(studentEntity.isPresent()){
            //then only delete
            Optional<Courses> course = coursesRepository.findById(courseId);
            if(course.isPresent()){
                //then only remove from course
                studentEntity.get().getCourses().remove(course.get());
                course.get().getPersons().remove(studentEntity.get());
                personRepository.save(studentEntity.get());
            }
        }
        modelAndView.setViewName("redirect:/admin/viewStudents?id="+courseId);
        return modelAndView;
    }
}
