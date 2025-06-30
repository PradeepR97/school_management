package com.schoolmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Data Transfer Object for Announcement.
 */
@Data
public class AnnouncementDTO {
    private Long id;
    private String title;
    private String content;
    private String postedBy;
    private LocalDateTime postedAt;
}
