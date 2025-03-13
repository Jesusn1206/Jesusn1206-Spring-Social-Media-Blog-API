package com.example.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.entity.Message;
import com.example.repository.AccountRepository;
import com.example.repository.MessageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private AccountRepository accountRepository;

    public Optional<Message> createMessage(Message message) {
        // Ensure message text is valid and user exists
        if (message.getMessageText().isBlank() || message.getMessageText().length() > 255 ||
                accountRepository.findById(message.getPostedBy()).isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(messageRepository.save(message));
    }

    public List<Message> getAllMessages() {
        return messageRepository.findAll();
    }

    public Optional<Message> getMessageById(int messageId) {
        return messageRepository.findById(messageId);
    }

    public boolean deleteMessage(int messageId) {
        if (messageRepository.existsById(messageId)) {
            messageRepository.deleteById(messageId);
            return true;
        }
        return false;
    }

    public boolean updateMessage(int messageId, String newText) {
        Optional<Message> existingMessage = messageRepository.findById(messageId);
        if (existingMessage.isPresent() && !newText.isBlank() && newText.length() <= 255) {
            Message message = existingMessage.get();
            message.setMessageText(newText);
            messageRepository.save(message);
            return true;
        }
        return false;
    }

    public List<Message> getMessagesByUser(int accountId) {
        return messageRepository.findByPostedBy(accountId);
    }
}
