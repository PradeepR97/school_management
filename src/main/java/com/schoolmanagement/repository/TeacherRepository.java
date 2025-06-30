package com.schoolmanagement.repository;

import com.schoolmanagement.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Teacher.
 */
@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {
}
