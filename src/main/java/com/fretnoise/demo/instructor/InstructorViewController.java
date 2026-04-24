package com.fretnoise.demo.instructor;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/instructors")
public class InstructorViewController {

    private final InstructorController instructorController;

    public InstructorViewController(InstructorController instructorController) {
        this.instructorController = instructorController;
    }

    @GetMapping("/view")
    public String showInstructors(Model model) {
        model.addAttribute("instructors", instructorController.listInstructors());
        return "instructors";
    }
}
