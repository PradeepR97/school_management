package com.schoolmanagement.service;

import com.schoolmanagement.dto.ClassRoomDTO;
import com.schoolmanagement.entity.ClassRoom;
import com.schoolmanagement.entity.Teacher;
import com.schoolmanagement.repository.ClassRoomRepository;
import com.schoolmanagement.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassRoomService {

    private final ClassRoomRepository classRoomRepository;
    private final TeacherRepository teacherRepository;

    public ClassRoomDTO create(ClassRoomDTO dto) {
        Teacher teacher = teacherRepository.findById(dto.getAssignedTeacherId())
                .orElseThrow(() -> new RuntimeException("Teacher not found"));
        ClassRoom cr = ClassRoom.builder()
                .name(dto.getName())
                .gradeLevel(dto.getGradeLevel())
                .assignedTeacher(teacher)
                .build();
        ClassRoom saved = classRoomRepository.save(cr);
        return toDto(saved);
    }

    public List<ClassRoomDTO> findAll() {
        return classRoomRepository.findAll().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public Optional<ClassRoomDTO> findById(Long id) {
        return classRoomRepository.findById(id).map(this::toDto);
    }

    public Optional<ClassRoomDTO> update(Long id, ClassRoomDTO dto) {
        return classRoomRepository.findById(id).map(existing -> {
            existing.setName(dto.getName());
            existing.setGradeLevel(dto.getGradeLevel());
            Teacher teacher = teacherRepository.findById(dto.getAssignedTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            existing.setAssignedTeacher(teacher);
            ClassRoom updated = classRoomRepository.save(existing);
            return toDto(updated);
        });
    }

    public boolean delete(Long id) {
        if (!classRoomRepository.existsById(id)) return false;
        classRoomRepository.deleteById(id);
        return true;
    }

    private ClassRoomDTO toDto(ClassRoom cr) {
        ClassRoomDTO dto = new ClassRoomDTO();
        dto.setId(cr.getId());
        dto.setName(cr.getName());
        dto.setGradeLevel(cr.getGradeLevel());
        dto.setAssignedTeacherId(cr.getAssignedTeacher().getId());
        return dto;
    }
}
