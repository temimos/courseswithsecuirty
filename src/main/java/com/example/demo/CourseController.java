package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


import javax.validation.Valid;

@Controller
public class CourseController {

    @Autowired
    CourseRepository courseRepository;

    @Autowired
    InstructorRepository instructorRepository;

    @Autowired
    private UserService userService;


    @RequestMapping("/")
    public String listCourses(Model model){
        model.addAttribute("courses", courseRepository.findAll());
        if(userService.getUser() != null) {
            model.addAttribute("user_id", userService.getUser().getId());
        }
        return "list";
    }


    @GetMapping("/add")
    public String courseForm(Model model) {
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("course", new Course());
        return "courseform";
    }

    @PostMapping("/process")
    public String processForm(@Valid Course course, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("instructors", instructorRepository.findAll());
            return "courseform";
        }
//        course.setUser(userService.getUser());
        courseRepository.save(course);
        return "redirect:/";
    }

    @GetMapping("/addInstructor")
    public String instructorForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructorform";
    }

    @PostMapping("/processInstructor")
    public String processInstructor(@Valid Instructor instructor, BindingResult result) {
        if (result.hasErrors()) {
            return "instructorform";
        }
        instructorRepository.save(instructor);
        return "redirect:/";
    }

    @RequestMapping("/detail/{id}")
    public String showCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("course", courseRepository.findById(id).get());
        return "show";
    }

    @RequestMapping("/update/{id}")
    public String updateCourse(@PathVariable("id") long id, Model model) {
        model.addAttribute("instructors", instructorRepository.findAll());
        model.addAttribute("course", courseRepository.findById(id));
        return "courseform";
    }

    @RequestMapping("/delete/{id}")
    public String delCourse(@PathVariable("id") long id) {
        courseRepository.deleteById(id);
        return "redirect:/";
    }

}