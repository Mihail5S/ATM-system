package org.example.storage.consumer;

import org.example.storage.dto.AccountEventDto;
import org.example.storage.service.EventStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventConsumer {

    @Autowired
    private EventStorageService storageService;

    @KafkaListener(topics = "${storage.topic.account}", containerFactory = "accountKafkaListenerContainerFactory")
    public void consume(AccountEventDto dto) {
        try {
            storageService.storeAccountEvent(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
