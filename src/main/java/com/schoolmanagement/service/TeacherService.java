package com.schoolmanagement.service;

import com.schoolmanagement.dto.TeacherDTO;
import com.schoolmanagement.entity.Teacher;
import com.schoolmanagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for managing Teachers.
 */
@Service
@RequiredArgsConstructor
public class TeacherService {

    private final TeacherRepository teacherRepository;

    public TeacherDTO createTeacher(TeacherDTO dto) {
        Teacher teacher = Teacher.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .subject(dto.getSubject())
                .build();

        Teacher saved = teacherRepository.save(teacher);
        return toDto(saved);
    }

    public List<TeacherDTO> getAllTeachers() {
        return teacherRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<TeacherDTO> getTeacherById(Long id) {
        return teacherRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<TeacherDTO> updateTeacher(Long id, TeacherDTO dto) {
        return teacherRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setEmail(dto.getEmail());
                    existing.setSubject(dto.getSubject());
                    Teacher updated = teacherRepository.save(existing);
                    return toDto(updated);
                });
    }

    public boolean deleteTeacher(Long id) {
        if (!teacherRepository.existsById(id)) {
            return false;
        }
        teacherRepository.deleteById(id);
        return true;
    }

    private TeacherDTO toDto(Teacher t) {
        TeacherDTO dto = new TeacherDTO();
        dto.setId(t.getId());
        dto.setFirstName(t.getFirstName());
        dto.setLastName(t.getLastName());
        dto.setEmail(t.getEmail());
        dto.setSubject(t.getSubject());
        return dto;
    }
}
