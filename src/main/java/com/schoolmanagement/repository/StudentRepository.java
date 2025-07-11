package com.schoolmanagement.repository;

import com.schoolmanagement.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Student.
 */
@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
