package com.schoolmanagement.service;

import com.schoolmanagement.dto.StudentDTO;
import com.schoolmanagement.entity.Student;
import com.schoolmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for managing Students.
 */
@Service
@RequiredArgsConstructor
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentDTO createStudent(StudentDTO dto) {
        Student student = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .grade(dto.getGrade())
                .build();

        Student saved = studentRepository.save(student);
        return toDto(saved);
    }

    public List<StudentDTO> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        return studentRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO dto) {
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setEmail(dto.getEmail());
                    existing.setGrade(dto.getGrade());
                    Student updated = studentRepository.save(existing);
                    return toDto(updated);
                });
    }

    public boolean deleteStudent(Long id) {
        if (!studentRepository.existsById(id)) {
            return false;
        }
        studentRepository.deleteById(id);
        return true;
    }

    private StudentDTO toDto(Student s) {
        StudentDTO dto = new StudentDTO();
        dto.setId(s.getId());
        dto.setFirstName(s.getFirstName());
        dto.setLastName(s.getLastName());
        dto.setEmail(s.getEmail());
        dto.setGrade(s.getGrade());
        return dto;
    }
}
