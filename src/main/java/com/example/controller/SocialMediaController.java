package com.example.controller;

import com.example.service.*;
import com.example.entity.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.ArrayList;
import org.springframework.http.ResponseEntity;
import com.example.exception.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;
//import org.springframework.web.bind.annotation.ResponseStatus;






/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */

@RestController
public class SocialMediaController {

    private AccountService accountService;
    private MessageService messageService;

    public SocialMediaController(AccountService accountService, MessageService messageService) {
        this.accountService = accountService;
        this.messageService = messageService;
    }

    @PostMapping("/register")
    public ResponseEntity<Account> register(@RequestBody Account account) {

        int registrationStatus = accountService.register(account);
        if(registrationStatus == 200) {
            return new ResponseEntity<>(HttpStatus.OK);
        } else if(registrationStatus == 400) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }








        return new ResponseEntity<>(HttpStatus.CONFLICT);
    }


    @PostMapping("/login")
    public ResponseEntity<Account> login(@RequestBody Account account) {
        if(accountService.login(account) != null) {
            return new ResponseEntity<>(accountService.login(account), HttpStatus.OK);
        }
        return new ResponseEntity<>(accountService.login(account), HttpStatus.UNAUTHORIZED);
    }




    @PostMapping("/messages")
    public ResponseEntity<Message> createMessage(@RequestBody Message message) {
        if(messageService.createMessage(message) != null) {
            return new ResponseEntity<>(message, HttpStatus.OK);
        }


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }



    @GetMapping("/messages")
    public List<Message> getAllMessages() {



        return messageService.getAllMessages();
    }

    @GetMapping("/messages/{message_id}")
    public ResponseEntity<Message> getMessageById(@PathVariable Integer message_id) {



        return new ResponseEntity<>(messageService.getMessageById(message_id), HttpStatus.OK);
    }



    @DeleteMapping("/messages/{message_id}")
    public ResponseEntity<String> deleteMessageById(@PathVariable Integer message_id) {
        if(messageService.getMessageById(message_id) == null) {
            return ResponseEntity.ok().body("");
        }

        messageService.deleteMessage(message_id);
        return ResponseEntity.ok().body("1");
    }


    @PatchMapping("/messages/{message_id}")
    public ResponseEntity<String> updateMessageById(@PathVariable Integer message_id, @RequestBody Message messageText) {

        if(messageService.getMessageById(message_id) == null) {
            return ResponseEntity.badRequest().body("");
        }


        if(messageService.patchMessage(message_id, messageText.getMessageText()) != null) {
            return ResponseEntity.ok().body("1");
        }

        
        return ResponseEntity.badRequest().body("");
    }


    @GetMapping("/accounts/{account_id}/messages")
    public List<Message> getMessageByAccount(@PathVariable Integer account_id) {



        return messageService.getAllMessagesByAccount(account_id);
    }







}
