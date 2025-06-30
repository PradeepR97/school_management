package com.schoolmanagement.service;

import com.schoolmanagement.dto.MessageDTO;
import com.schoolmanagement.entity.Message;
import com.schoolmanagement.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageDTO sendMessage(String sender, MessageDTO dto) {
        Message message = Message.builder()
                .senderUsername(sender)
                .recipientUsername(dto.getRecipientUsername())
                .content(dto.getContent())
                .sentAt(LocalDateTime.now())
                .build();
        return toDto(messageRepository.save(message));
    }

    public List<MessageDTO> getInbox(String recipientUsername) {
        return messageRepository.findByRecipientUsername(recipientUsername)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    public List<MessageDTO> getSent(String senderUsername) {
        return messageRepository.findBySenderUsername(senderUsername)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    private MessageDTO toDto(Message m) {
        MessageDTO dto = new MessageDTO();
        dto.setId(m.getId());
        dto.setSenderUsername(m.getSenderUsername());
        dto.setRecipientUsername(m.getRecipientUsername());
        dto.setContent(m.getContent());
        dto.setSentAt(m.getSentAt());
        return dto;
    }
}
