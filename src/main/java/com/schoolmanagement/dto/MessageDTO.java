package com.schoolmanagement.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDTO {
    private Long id;
    private String senderUsername;
    private String recipientUsername;
    private String content;
    private LocalDateTime sentAt;
}
