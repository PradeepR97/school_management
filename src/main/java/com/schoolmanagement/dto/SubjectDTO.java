package com.schoolmanagement.dto;

import lombok.Data;

/**
 * DTO for Subject.
 */
@Data
public class SubjectDTO {
    private Long id;
    private String name;
    private Long teacherId;
    private Long classRoomId;
}
