package com.fretnoise.demo.course;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/courses")
public class CourseController {

    private final Map<String, Course> courses = new ConcurrentHashMap<>();

    public CourseController() {
        courses.put("CS101", new Course("Introduction to Computer Science", "CS101", List.of(), 10, "mixed", "Main Campus"));
        courses.put("CS201", new Course("Data Structures", "CS201", List.of("CS101"), 12, "exam", "Main Campus"));
        courses.put("MATH201", new Course("Linear Algebra", "MATH201", List.of(), 10, "exam", "North Campus"));
        courses.put("MATH301", new Course("Calculus III", "MATH301", List.of("MATH201"), 12, "exam", "North Campus"));
        courses.put("PHYS301", new Course("Classical Mechanics", "PHYS301", List.of("MATH201"), 14, "mixed", "Science Campus"));
        courses.put("PHYS401", new Course("Quantum Mechanics", "PHYS401", List.of("PHYS301", "MATH301"), 14, "exam", "Science Campus"));
        courses.put("ENG102", new Course("English Composition", "ENG102", List.of(), 8, "coursework", "Arts Campus"));
        courses.put("ENG201", new Course("Literature Survey", "ENG201", List.of("ENG102"), 10, "coursework", "Arts Campus"));
        courses.put("CHEM101", new Course("General Chemistry", "CHEM101", List.of(), 10, "mixed", "Science Campus"));
        courses.put("CHEM201", new Course("Organic Chemistry", "CHEM201", List.of("CHEM101"), 12, "exam", "Science Campus"));
        courses.put("BIO102", new Course("General Biology", "BIO102", List.of("CHEM101"), 10, "coursework", "Science Campus"));
        courses.put("HIST101", new Course("World History", "HIST101", List.of(), 8, "coursework", "Main Campus"));
        courses.put("HIST202", new Course("American History", "HIST202", List.of("HIST101"), 10, "coursework", "Main Campus"));
        courses.put("ART101", new Course("Introduction to Art", "ART101", List.of(), 8, "coursework", "Arts Campus"));
    }

    @GetMapping(produces = "application/json")
    public List<Course> listCourses() {
        return new ArrayList<>(courses.values());
    }

    @GetMapping(value = "{code}", produces = "application/json")
    public ResponseEntity<Course> getCourse(@PathVariable String code) {
        Course course = courses.get(code);
        return course != null ? ResponseEntity.ok(course) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Course> createCourse(@RequestBody Course course) {
        if (course == null || course.code() == null || course.code().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (courses.containsKey(course.code())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        courses.put(course.code(), course);
        return ResponseEntity.status(HttpStatus.CREATED).body(course);
    }

    @PutMapping(value = "{code}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Course> updateCourse(@PathVariable String code, @RequestBody Course course) {
        if (course == null || course.code() == null || !code.equals(course.code())) {
            return ResponseEntity.badRequest().build();
        }

        if (!courses.containsKey(code)) {
            return ResponseEntity.notFound().build();
        }

        courses.put(code, course);
        return ResponseEntity.ok(course);
    }

    @DeleteMapping(value = "{code}")
    public ResponseEntity<Void> deleteCourse(@PathVariable String code) {
        if (courses.remove(code) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
