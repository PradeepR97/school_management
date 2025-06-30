package com.schoolmanagement.service;

import com.schoolmanagement.dto.AttendanceDTO;
import com.schoolmanagement.entity.Attendance;
import com.schoolmanagement.entity.Student;
import com.schoolmanagement.repository.AttendanceRepository;
import com.schoolmanagement.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for managing Attendance.
 */
@Service
@RequiredArgsConstructor
public class AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final StudentRepository studentRepository;

    public AttendanceDTO markAttendance(AttendanceDTO dto) {
        Student student = studentRepository.findById(dto.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Attendance record = Attendance.builder()
                .student(student)
                .date(dto.getDate())
                .status(dto.getStatus())
                .build();

        Attendance saved = attendanceRepository.save(record);
        return toDto(saved);
    }

    public List<AttendanceDTO> getByStudent(Long studentId) {
        return attendanceRepository.findByStudentId(studentId)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<AttendanceDTO> getByDate(LocalDate date) {
        return attendanceRepository.findByDate(date)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private AttendanceDTO toDto(Attendance a) {
        AttendanceDTO dto = new AttendanceDTO();
        dto.setId(a.getId());
        dto.setStudentId(a.getStudent().getId());
        dto.setDate(a.getDate());
        dto.setStatus(a.getStatus());
        return dto;
    }
    public boolean deleteAttendance(Long id) {
        if (!attendanceRepository.existsById(id)) return false;
        attendanceRepository.deleteById(id);
        return true;
    }

}
