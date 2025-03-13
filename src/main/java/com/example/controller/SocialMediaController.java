package com.example.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.entity.Account;
import com.example.entity.Message;
import com.example.service.AccountService;
import com.example.service.MessageService;

import java.util.List;
import java.util.Optional;



/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@RestController
@RequestMapping("/")
public class SocialMediaController {
 @Autowired
    private AccountService accountService;

    @Autowired
    private MessageService messageService;

    // 1. Register a new user
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Account account) {
        Optional<Account> newAccount = accountService.registerAccount(account);
        if (newAccount.isPresent()) {
            return ResponseEntity.ok(newAccount.get());
        }
        return ResponseEntity.status(400).build();
    }

    
    // 2. User login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Account account) {
        Optional<Account> existingAccount = accountService.login(account.getUsername(), account.getPassword());
        if (existingAccount.isPresent()) {
            return ResponseEntity.ok(existingAccount.get());
        }
        return ResponseEntity.status(401).build();
    }

    // 3. Create a new message
    @PostMapping("/messages")
    public ResponseEntity<?> createMessage(@RequestBody Message message) {
        Optional<Message> newMessage = messageService.createMessage(message);
        if (newMessage.isPresent()) {
            return ResponseEntity.ok(newMessage.get());
        }
        return ResponseEntity.status(400).build();
    }

    // 4. Retrieve all messages
    @GetMapping("/messages")
    public ResponseEntity<List<Message>> getAllMessages() {
        return ResponseEntity.ok(messageService.getAllMessages());
    }

    // 5. Retrieve a message by its ID
    @GetMapping("/messages/{messageId}")
    public ResponseEntity<?> getMessageById(@PathVariable int messageId) {
        Optional<Message> message = messageService.getMessageById(messageId);
        return message.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.ok().build());
    }

    // 6. Delete a message by ID
    @DeleteMapping("/messages/{messageId}")
    public ResponseEntity<?> deleteMessage(@PathVariable int messageId) {
        boolean deleted = messageService.deleteMessage(messageId);
        return deleted ? ResponseEntity.ok(1) : ResponseEntity.ok().build();
    }

    // 7. Update a message's text
    @PatchMapping("/messages/{messageId}")
    public ResponseEntity<?> updateMessage(@PathVariable int messageId, @RequestBody Message message) {
        boolean updated = messageService.updateMessage(messageId, message.getMessageText());
        return updated ? ResponseEntity.ok(1) : ResponseEntity.status(400).build();
    }

    // 8. Retrieve all messages by a user
    @GetMapping("/accounts/{accountId}/messages")
    public ResponseEntity<List<Message>> getMessagesByUser(@PathVariable int accountId) {
        return ResponseEntity.ok(messageService.getMessagesByUser(accountId));
    }
}


