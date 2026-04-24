package com.fretnoise.demo.instructor;

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
@RequestMapping("/instructors")
public class InstructorController {

    private final Map<String, Instructor> instructors = new ConcurrentHashMap<>();

    public InstructorController() {
        instructors.put("jane.doe@example.com", new Instructor("Jane Doe", "jane.doe@example.com", List.of("CS101", "MATH201")));
        instructors.put("john.smith@example.com", new Instructor("John Smith", "john.smith@example.com", List.of("CS101", "PHYS301")));
        instructors.put("alice.johnson@example.com", new Instructor("Alice Johnson", "alice.johnson@example.com", List.of("ENG102", "ENG201")));
        instructors.put("bob.wilson@example.com", new Instructor("Bob Wilson", "bob.wilson@example.com", List.of("CHEM101", "CHEM201")));
        instructors.put("carol.davis@example.com", new Instructor("Carol Davis", "carol.davis@example.com", List.of("BIO102")));
        instructors.put("david.miller@example.com", new Instructor("David Miller", "david.miller@example.com", List.of("HIST101", "HIST202")));
        instructors.put("emma.brown@example.com", new Instructor("Emma Brown", "emma.brown@example.com", List.of("ART101")));
        instructors.put("frank.garcia@example.com", new Instructor("Frank Garcia", "frank.garcia@example.com", List.of("PHYS301", "PHYS401")));
    }

    @GetMapping(produces = "application/json")
    public List<Instructor> listInstructors() {
        return new ArrayList<>(instructors.values());
    }

    @GetMapping(value = "{email}", produces = "application/json")
    public ResponseEntity<Instructor> getInstructor(@PathVariable String email) {
        Instructor instructor = instructors.get(email);
        return instructor != null ? ResponseEntity.ok(instructor) : ResponseEntity.notFound().build();
    }

    @PostMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Instructor> createInstructor(@RequestBody Instructor instructor) {
        if (instructor == null || instructor.email() == null || instructor.email().isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (instructors.containsKey(instructor.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        instructors.put(instructor.email(), instructor);
        return ResponseEntity.status(HttpStatus.CREATED).body(instructor);
    }

    @PutMapping(value = "{email}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Instructor> updateInstructor(@PathVariable String email, @RequestBody Instructor instructor) {
        if (instructor == null || instructor.email() == null || !email.equals(instructor.email())) {
            return ResponseEntity.badRequest().build();
        }

        if (!instructors.containsKey(email)) {
            return ResponseEntity.notFound().build();
        }

        instructors.put(email, instructor);
        return ResponseEntity.ok(instructor);
    }

    @DeleteMapping(value = "{email}")
    public ResponseEntity<Void> deleteInstructor(@PathVariable String email) {
        if (instructors.remove(email) != null) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
