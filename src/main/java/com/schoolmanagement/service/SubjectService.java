package com.schoolmanagement.service;

import com.schoolmanagement.dto.SubjectDTO;
import com.schoolmanagement.entity.ClassRoom;
import com.schoolmanagement.entity.Subject;
import com.schoolmanagement.entity.Teacher;
import com.schoolmanagement.repository.ClassRoomRepository;
import com.schoolmanagement.repository.SubjectRepository;
import com.schoolmanagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Business logic for managing Subjects.
 */
@Service
@RequiredArgsConstructor
public class SubjectService {

    private final SubjectRepository subjectRepository;
    private final TeacherRepository teacherRepository;
    private final ClassRoomRepository classRoomRepository;

    public SubjectDTO create(SubjectDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        ClassRoom classRoom = classRoomRepository.findById(dto.getClassRoomId())
                .orElseThrow(() -> new RuntimeException("ClassRoom not found"));

        Subject s = Subject.builder()
                .name(dto.getName())
                .teacher(teacher)
                .classRoom(classRoom)
                .build();

        Subject saved = subjectRepository.save(s);
        return toDto(saved);
    }

    public List<SubjectDTO> findAll() {
        return subjectRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<SubjectDTO> findById(Long id) {
        return subjectRepository.findById(id).map(this::toDto);
    }

    public Optional<SubjectDTO> update(Long id, SubjectDTO dto) {
        return subjectRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            Teacher teacher = teacherRepository.findById(dto.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            ClassRoom classRoom = classRoomRepository.findById(dto.getClassRoomId())
                    .orElseThrow(() -> new RuntimeException("ClassRoom not found"));
            existing.setTeacher(teacher);
            existing.setClassRoom(classRoom);
            Subject updated = subjectRepository.save(existing);
            return toDto(updated);
        });
    }

    public boolean delete(Long id) {
        if (!subjectRepository.existsById(id)) return false;
        subjectRepository.deleteById(id);
        return true;
    }

    private SubjectDTO toDto(Subject s) {
        SubjectDTO dto = new SubjectDTO();
        dto.setId(s.getId());
        dto.setName(s.getName());
        dto.setTeacherId(s.getTeacher().getId());
        dto.setClassRoomId(s.getClassRoom().getId());
        return dto;
    }
}
