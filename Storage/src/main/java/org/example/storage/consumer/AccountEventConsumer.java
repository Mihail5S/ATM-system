package org.example.storage.consumer;

import org.example.storage.dto.AccountEventDto;
import org.example.storage.service.EventStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class AccountEventConsumer {

    private EventStorageService storageService;

    public AccountEventConsumer(EventStorageService storageService) {
        this.storageService = storageService;
    }

    @KafkaListener(topics = "${storage.topic.account}", containerFactory = "accountKafkaListenerContainerFactory")
    public void consume(AccountEventDto dto) {
        try {
            storageService.storeAccountEvent(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}
