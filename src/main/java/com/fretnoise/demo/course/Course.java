package com.fretnoise.demo.course;

import java.util.List;

public record Course(
    String name,
    String code,
    List<String> prerequisites,
    int courseLengthWeeks,
    String assessmentType,
    String campus
) {
}
