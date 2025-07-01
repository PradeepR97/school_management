package com.schoolmanagement.dto;
import jakarta.validation.constraints.*; // For annotations
import lombok.Data;

/**
 * Data Transfer Object for Student.
 */
@Data
public class StudentDTO {
    private Long id;
    @NotBlank(message = "First name is required")// include ID for GET/PUT responses
    private String firstName;
    private String lastName;
    @Email(message = "Invalid email format")
    private String email;
    private String grade;
}
