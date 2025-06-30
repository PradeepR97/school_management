package com.schoolmanagement.controller;

import com.schoolmanagement.dto.MessageDTO;
import com.schoolmanagement.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/messages")
@RequiredArgsConstructor
public class MessageController {

    private final MessageService messageService;

    // Send a message (Teachers or Parents)
    @PostMapping
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<MessageDTO> sendMessage(@RequestBody MessageDTO dto, Principal principal) {
        MessageDTO result = messageService.sendMessage(principal.getName(), dto);
        return ResponseEntity.ok(result);
    }

    // Inbox for logged-in user
    @GetMapping("/inbox")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<List<MessageDTO>> inbox(Principal principal) {
        List<MessageDTO> messages = messageService.getInbox(principal.getName());
        return ResponseEntity.ok(messages);
    }

    // Sent messages for logged-in user
    @GetMapping("/sent")
    @PreAuthorize("hasAnyRole('TEACHER', 'PARENT')")
    public ResponseEntity<List<MessageDTO>> sent(Principal principal) {
        List<MessageDTO> messages = messageService.getSent(principal.getName());
        return ResponseEntity.ok(messages);
    }
}
