package com.schoolmanagement.service;

import com.schoolmanagement.dto.StudentDTO;
import com.schoolmanagement.entity.Student;
import com.schoolmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import com.schoolmanagement.constants.MessageConstants;

/**
 * Business logic for managing Students.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentDTO createStudent(StudentDTO dto) {
        log.info("Creating student with email: {}", dto.getEmail());

        Student student = Student.builder()
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .grade(dto.getGrade())
                .build();

        Student saved = studentRepository.save(student);
        log.debug("Student created with ID: {}", saved.getId());
        log.info(MessageConstants.STUDENT_CREATE, dto.getEmail());
        log.debug(MessageConstants.STUDENT_CREATED, saved.getId());
        return toDto(saved);
    }

    public List<StudentDTO> getAllStudents() {
        log.info("Fetching all students");
        log.info(MessageConstants.FETCH_ALL_STUDENTS);
        return studentRepository.findAll()
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<StudentDTO> getStudentById(Long id) {
        log.info("Fetching student by ID: {}", id);
        return studentRepository.findById(id)
                .map(this::toDto);
    }

    public Optional<StudentDTO> updateStudent(Long id, StudentDTO dto) {
        log.info("Updating student ID: {}", id);
        return studentRepository.findById(id)
                .map(existing -> {
                    existing.setFirstName(dto.getFirstName());
                    existing.setLastName(dto.getLastName());
                    existing.setEmail(dto.getEmail());
                    existing.setGrade(dto.getGrade());
                    Student updated = studentRepository.save(existing);
                    log.debug("Student updated: {}", updated.getId());
                    return toDto(updated);
                });
    }

    public boolean deleteStudent(Long id) {
        log.warn("Deleting student ID: {}", id);
        log.warn(MessageConstants.DELETE_STUDENT, id);
        if (!studentRepository.existsById(id)) {
            log.error("Student with ID {} not found", id);
            log.error(MessageConstants.STUDENT_NOT_FOUND_WITH_ID, id);
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
