package com.fretnoise.demo.course;

import com.fretnoise.demo.instructor.Instructor;
import com.fretnoise.demo.instructor.InstructorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/courses")
public class CourseViewController {

    private final CourseController courseController;
    private final InstructorController instructorController;

    public CourseViewController(CourseController courseController, InstructorController instructorController) {
        this.courseController = courseController;
        this.instructorController = instructorController;
    }

    @GetMapping("/view/{code}")
    public String showCourseDetails(@PathVariable String code, Model model) {
        var courseResponse = courseController.getCourse(code);
        
        if (courseResponse.getStatusCode().is4xxClientError()) {
            return "redirect:/courses/view";
        }

        Course course = courseResponse.getBody();
        List<Instructor> instructors = instructorController.listInstructors()
            .stream()
            .filter(i -> i.courseCodes().contains(code))
            .toList();

        model.addAttribute("course", course);
        model.addAttribute("instructors", instructors);
        return "course-details";
    }
}
