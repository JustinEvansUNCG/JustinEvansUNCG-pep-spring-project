package com.example.service;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.repository.MessageRepository;


import com.example.controller.*;
import com.example.entity.*;
import com.example.repository.AccountRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;



@Service
public class MessageService {



    private final MessageRepository messageRepository;
    private final AccountRepository accountRepository;

    @Autowired
    public MessageService(MessageRepository messageRepository, AccountRepository accountRepository) {
        this.messageRepository = messageRepository;
        this.accountRepository = accountRepository;
    }

    public List<Message> getAllMessages() {
        
        return messageRepository.findAll();
    }


    public List<Message> getAllMessagesByAccount(Integer account_id) {
        
        return messageRepository.findAllByPostedBy(account_id);
    }


    public Message getMessageById(Integer id) throws RuntimeException {
        return messageRepository.findById(id)
                .orElse(null);


    }

    public void deleteMessage(Integer id) throws RuntimeException {
        messageRepository.deleteById(id);
    }

    public Message patchMessage(Integer id, String message_text) throws RuntimeException {
        Message message = messageRepository.findById(id)
                .orElse(null);
        if(message_text.length() < 256 && message_text.length() > 0) {

            message.setMessageText(message_text);
            messageRepository.save(message);
            return message;
            
        }
        return null;
    }

    public Message createMessage(Message message) {
        Account account = accountRepository.findById(message.getPostedBy())
                .orElse(null);

        if(message.getMessageText().length() < 256 && message.getMessageText().length() > 0 && account != null) {
            messageRepository.save(message);
            return message;
        }
        
        return null;
    }




}
