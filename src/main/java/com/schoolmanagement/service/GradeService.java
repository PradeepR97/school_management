package com.schoolmanagement.service;

import com.schoolmanagement.dto.GradeDTO;
import com.schoolmanagement.entity.Grade;
import com.schoolmanagement.entity.Student;
import com.schoolmanagement.entity.Subject;
import com.schoolmanagement.repository.GradeRepository;
import com.schoolmanagement.repository.StudentRepository;
import com.schoolmanagement.repository.SubjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService {

    private final GradeRepository gradeRepository;
    private final StudentRepository studentRepository;
    private final SubjectRepository subjectRepository;

    public GradeDTO submitGrade(GradeDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Subject subject = subjectRepository.findById(dto.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        Grade grade = Grade.builder()
                .student(student)
                .subject(subject)
                .score(dto.getScore())
                .comments(dto.getComments())
                .build();

        Grade saved = gradeRepository.save(grade);
        return toDto(saved);
    }

    public List<GradeDTO> getByStudent(Long studentId) {
        return gradeRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<GradeDTO> getBySubject(Long subjectId) {
        return gradeRepository.findBySubjectId(subjectId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public boolean deleteGrade(Long id) {
        if (!gradeRepository.existsById(id)) return false;
        gradeRepository.deleteById(id);
        return true;
    }

    private GradeDTO toDto(Grade g) {
        GradeDTO dto = new GradeDTO();
        dto.setId(g.getId());
        dto.setStudentId(g.getStudent().getId());
        dto.setSubjectId(g.getSubject().getId());
        dto.setScore(g.getScore());
        dto.setComments(g.getComments());
        return dto;
    }
}
