    package com.schoolmanagement.controller;
    
    import com.schoolmanagement.dto.AnnouncementDTO;
    import com.schoolmanagement.service.AnnouncementService;
    import lombok.RequiredArgsConstructor;
    import org.springframework.http.ResponseEntity;
    import org.springframework.security.access.prepost.PreAuthorize;
    import org.springframework.web.bind.annotation.*;
    
    import java.security.Principal;
    import java.util.List;
    
    /**
     * REST controller for managing Announcements.
     */
    @RestController
    @RequestMapping("/api/announcements")
    @RequiredArgsConstructor
    public class AnnouncementController {
    
        private final AnnouncementService announcementService;
    
        /**
         * Create a new announcement.
         * Only ADMIN role can post announcements.
         */
        @PostMapping
        @PreAuthorize("hasRole('ADMIN')")
        public ResponseEntity<AnnouncementDTO> create(
                @RequestBody AnnouncementDTO dto,
                Principal principal) {
            // Uses the authenticated username as postedBy
            AnnouncementDTO created = announcementService.create(dto, principal.getName());
            return ResponseEntity.ok(created);
        }
    
        /**
         * List all announcements.
         * Any authenticated user can view.
         */
        @GetMapping
        @PreAuthorize("isAuthenticated()")
        public ResponseEntity<List<AnnouncementDTO>> listAll() {
            List<AnnouncementDTO> all = announcementService.listAll();
            return ResponseEntity.ok(all);
        }
    }
