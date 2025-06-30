package com.schoolmanagement.dto;

import lombok.Data;

@Data
public class ClassRoomDTO {
    private Long id;
    private String name;
    private String gradeLevel;
    private Long assignedTeacherId;  // reference to a Teacher
}
