package com.schoolmanagement.controller;

import com.schoolmanagement.dto.GradeDTO;
import com.schoolmanagement.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Grades.
 */
@RestController
@RequestMapping("/api/grades")
@RequiredArgsConstructor
public class GradeController {

    private final GradeService gradeService;

    /**
     * Submit a grade.
     * Only TEACHER or ADMIN.
     */
    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<GradeDTO> submit(@RequestBody GradeDTO dto) {
        return ResponseEntity.ok(gradeService.submitGrade(dto));
    }

    /**
     * Get grades for a student.
     * STUDENT (self), PARENT, ADMIN, TEACHER.
     */
    @GetMapping("/student/{studentId}")
    @PreAuthorize("hasAnyRole('STUDENT','ADMIN','TEACHER')")
    public ResponseEntity<List<GradeDTO>> byStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(gradeService.getByStudent(studentId));
    }

    /**
     * Get grades for a subject.
     * ADMIN, TEACHER.
     */
    @GetMapping("/subject/{subjectId}")
    @PreAuthorize("hasAnyRole('ADMIN','TEACHER')")
    public ResponseEntity<List<GradeDTO>> bySubject(@PathVariable Long subjectId) {
        return ResponseEntity.ok(gradeService.getBySubject(subjectId));
    }

    /**
     * Delete a grade record.
     */
    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('TEACHER','ADMIN')")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id) {
        boolean ok = gradeService.deleteGrade(id);
        if (!ok) {
            return ResponseEntity.status(404)
                    .body(Map.of("message", "Grade with id " + id + " not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Grade with id " + id + " deleted"));
    }
}
