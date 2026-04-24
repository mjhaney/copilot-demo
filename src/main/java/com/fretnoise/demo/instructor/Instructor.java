package com.fretnoise.demo.instructor;

import java.util.List;

public record Instructor(String name, String email, List<String> courseCodes) {
}
