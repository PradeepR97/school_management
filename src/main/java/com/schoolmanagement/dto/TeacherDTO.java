package com.schoolmanagement.dto;

import lombok.Data;

/**
 * Data Transfer Object for Teacher.
 */
@Data
public class TeacherDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String subject;
}
