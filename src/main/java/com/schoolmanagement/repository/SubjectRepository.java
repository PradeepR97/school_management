package com.schoolmanagement.repository;

import com.schoolmanagement.entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for Subject.
 */
@Repository
public interface SubjectRepository extends JpaRepository<Subject, Long> {
}
