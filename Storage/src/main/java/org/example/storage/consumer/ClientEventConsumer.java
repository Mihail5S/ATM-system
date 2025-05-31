package org.example.storage.consumer;

import org.example.storage.dto.ClientEventDto;
import org.example.storage.service.EventStorageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ClientEventConsumer {

    @Autowired
    private EventStorageService storageService;

    @KafkaListener(topics = "${storage.topic.client}", containerFactory = "clientKafkaListenerContainerFactory")
    public void consume(ClientEventDto dto) {
        try {
            storageService.storeClientEvent(dto);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}

