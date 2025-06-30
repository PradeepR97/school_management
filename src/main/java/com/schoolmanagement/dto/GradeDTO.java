package com.schoolmanagement.dto;

import lombok.Data;

/**
 * DTO for Grade.
 */
@Data
public class GradeDTO {
    private Long id;
    private Long studentId;
    private Long subjectId;
    private Double score;
    private String comments;
}
