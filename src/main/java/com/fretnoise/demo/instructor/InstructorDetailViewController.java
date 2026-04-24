package com.fretnoise.demo.instructor;

import com.fretnoise.demo.course.CourseController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/instructors")
public class InstructorDetailViewController {

    private final InstructorController instructorController;
    private final CourseController courseController;

    public InstructorDetailViewController(InstructorController instructorController, CourseController courseController) {
        this.instructorController = instructorController;
        this.courseController = courseController;
    }

    @GetMapping("/view/{email}")
    public String showInstructorDetails(@PathVariable String email, Model model) {
        var instructorResponse = instructorController.getInstructor(email);
        
        if (instructorResponse.getStatusCode().is4xxClientError()) {
            return "redirect:/instructors/view";
        }

        Instructor instructor = instructorResponse.getBody();
        var courses = instructor.courseCodes()
            .stream()
            .map(code -> courseController.getCourse(code).getBody())
            .toList();

        model.addAttribute("instructor", instructor);
        model.addAttribute("courses", courses);
        return "instructor-details";
    }
}
