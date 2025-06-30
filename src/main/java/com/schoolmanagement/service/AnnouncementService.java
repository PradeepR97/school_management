package com.schoolmanagement.service;

import com.schoolmanagement.dto.AnnouncementDTO;
import com.schoolmanagement.entity.Announcement;
import com.schoolmanagement.repository.AnnouncementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Business logic for managing Announcements.
 */
@Service
@RequiredArgsConstructor
public class AnnouncementService {

    private final AnnouncementRepository announcementRepository;

    /**
     * Create a new announcement.
     */
    public AnnouncementDTO create(AnnouncementDTO dto, String postedBy) {
        Announcement announcement = Announcement.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .postedBy(postedBy)
                .postedAt(LocalDateTime.now())
                .build();

        Announcement saved = announcementRepository.save(announcement);
        return toDto(saved);
    }

    /**
     * List all announcements, most recent first.
     */
    public List<AnnouncementDTO> listAll() {
        return announcementRepository.findAll().stream()
                .sorted((a, b) -> b.getPostedAt().compareTo(a.getPostedAt()))
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    /**
     * Convert entity to DTO.
     */
    private AnnouncementDTO toDto(Announcement a) {
        AnnouncementDTO dto = new AnnouncementDTO();
        dto.setId(a.getId());
        dto.setTitle(a.getTitle());
        dto.setContent(a.getContent());
        dto.setPostedBy(a.getPostedBy());
        dto.setPostedAt(a.getPostedAt());
        return dto;
    }
}
