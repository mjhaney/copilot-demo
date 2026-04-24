package com.fretnoise.demo.course;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/courses")
public class CoursesListViewController {

    private final CourseController courseController;

    public CoursesListViewController(CourseController courseController) {
        this.courseController = courseController;
    }

    @GetMapping
    public String showCourses(Model model) {
        model.addAttribute("courses", courseController.listCourses());
        return "courses-list";
    }
}
