package com.schoolmanagement.controller;

import com.schoolmanagement.dto.TeacherDTO;
import com.schoolmanagement.service.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Teacher CRUD operations.
 * Only users with role ADMIN can manage teachers.
 */
@RestController
@RequestMapping("/api/teachers")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class TeacherController {

    private final TeacherService teacherService;

    @PostMapping
    public ResponseEntity<TeacherDTO> create(@RequestBody TeacherDTO dto) {
        TeacherDTO created = teacherService.createTeacher(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<TeacherDTO>> listAll() {
        List<TeacherDTO> all = teacherService.getAllTeachers();
        return ResponseEntity.ok(all);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TeacherDTO> getById(@PathVariable Long id) {
        return teacherService.getTeacherById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<TeacherDTO> update(
            @PathVariable Long id,
            @RequestBody TeacherDTO dto) {

        return teacherService.updateTeacher(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> delete(@PathVariable Long id) {
        boolean deleted = teacherService.deleteTeacher(id);
        if (!deleted) {
            return ResponseEntity
                    .status(HttpStatus.NOT_FOUND)
                    .body(Map.of("message", "Teacher with id " + id + " not found"));
        }
        return ResponseEntity
                .ok(Map.of("message", "Teacher with id " + id + " successfully deleted"));
    }

}
