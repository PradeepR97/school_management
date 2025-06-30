package com.schoolmanagement.controller;

import com.schoolmanagement.dto.ClassRoomDTO;
import com.schoolmanagement.service.ClassRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/classrooms")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class ClassRoomController {

    private final ClassRoomService service;

    @PostMapping
    public ResponseEntity<ClassRoomDTO> create(@RequestBody ClassRoomDTO dto) {
        return ResponseEntity.ok(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<ClassRoomDTO>> list() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> get(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoomDTO> update(
            @PathVariable Long id,
            @RequestBody ClassRoomDTO dto) {

        return service.update(id, dto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        boolean ok = service.delete(id);
        if (!ok) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(Map.of("message", "ClassRoom with id " + id + " deleted"));
    }
}
