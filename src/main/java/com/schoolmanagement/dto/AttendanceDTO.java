package com.schoolmanagement.dto;

import com.schoolmanagement.entity.Attendance.Status;
import lombok.Data;

import java.time.LocalDate;

/**
 * Data Transfer Object for Attendance.
 */
@Data
public class AttendanceDTO {
    private Long id;           // for GET responses
    private Long studentId;
    private LocalDate date;
    private Status status;
}
