package com.schoolmanagement.repository;

import com.schoolmanagement.entity.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    List<Message> findByRecipientUsername(String recipientUsername);
    List<Message> findBySenderUsername(String senderUsername);
}
