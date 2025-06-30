package com.schoolmanagement.controller;

import com.schoolmanagement.dto.AttendanceDTO;
import com.schoolmanagement.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * REST controller for managing Attendance.
 */
@RestController
@RequestMapping("/api/attendance")
@RequiredArgsConstructor
public class AttendanceController {

    private final AttendanceService attendanceService;

    /**
     * Mark attendance for a student.
     * Only TEACHER or ADMIN can mark attendance.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<AttendanceDTO> mark(@RequestBody AttendanceDTO dto) {
        AttendanceDTO saved = attendanceService.markAttendance(dto);
        return ResponseEntity.ok(saved);
    }

    /**
     * View attendance for a specific student.
     * STUDENT can view their own; ADMIN/TEACHER can view any.
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER','STUDENT')")
    public ResponseEntity<List<AttendanceDTO>> byStudent(@PathVariable Long studentId) {
        List<AttendanceDTO> list = attendanceService.getByStudent(studentId);
        return ResponseEntity.ok(list);
    }

    /**
     * View attendance for a specific date.
     * Only ADMIN or TEACHER can view by date.
     */
    @GetMapping("/date/{date}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<List<AttendanceDTO>> byDate(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<AttendanceDTO> list = attendanceService.getByDate(date);
        return ResponseEntity.ok(list);
    }

    /**
     * Optionally, delete an attendance record.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        boolean deleted = attendanceService.deleteAttendance(id);
        if (!deleted) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Attendance record with id " + id + " not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Attendance record with id " + id + " deleted"));
    }
}
