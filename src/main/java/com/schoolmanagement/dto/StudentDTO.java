package com.schoolmanagement.dto;

import lombok.Data;

/**
 * Data Transfer Object for Student.
 */
@Data
public class StudentDTO {
    private Long id;           // include ID for GET/PUT responses
    private String firstName;
    private String lastName;
    private String email;
    private String grade;
}
