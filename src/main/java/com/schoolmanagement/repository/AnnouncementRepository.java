package com.schoolmanagement.repository;

import com.schoolmanagement.entity.Announcement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for Announcement entities.
 */
@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement, Long> {
}
