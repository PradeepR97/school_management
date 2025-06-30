package com.schoolmanagement.controller;

import com.schoolmanagement.dto.SubjectDTO;
import com.schoolmanagement.service.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * REST controller for Subject CRUD. Only ADMINs can manage subjects.
 */
@RestController
@RequestMapping("/api/subjects")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDTO> create(@RequestBody SubjectDTO dto) {
        SubjectDTO created = subjectService.create(dto);
        return ResponseEntity.ok(created);
    }

    @GetMapping
    public ResponseEntity<List<SubjectDTO>> listAll() {
        return ResponseEntity.ok(subjectService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectDTO> getById(@PathVariable Long id) {
        return subjectService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<SubjectDTO> update(
            @PathVariable Long id,
            @RequestBody SubjectDTO dto) {

        return subjectService.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String,String>> delete(@PathVariable Long id) {
        boolean ok = subjectService.delete(id);
        if (!ok) {
            return ResponseEntity
                    .status(404)
                    .body(Map.of("message", "Subject with id " + id + " not found"));
        }
        return ResponseEntity.ok(Map.of("message", "Subject with id " + id + " deleted"));
    }
}
