package org.example.storage.service;

import org.example.storage.dto.AccountEventDto;
import org.example.storage.dto.ClientEventDto;
import org.example.storage.model.AccountEventEntity;
import org.example.storage.model.ClientEventEntity;
import org.example.storage.repository.AccountEventRepository;
import org.example.storage.repository.ClientEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventStorageService {

    @Autowired
    private ClientEventRepository clientRepo;

    @Autowired
    private AccountEventRepository accountRepo;

    @Autowired
    private MappingService mappingService;

    @Autowired
    private ObjectMapper objectMapper;

    public void storeClientEvent(ClientEventDto dto) throws JsonProcessingException {
        String payloadJson = objectMapper.writeValueAsString(dto.getPayload());
        ClientEventEntity entity = mappingService.toEntity(dto, payloadJson);
        clientRepo.save(entity);
    }

    public void storeAccountEvent(AccountEventDto dto) throws JsonProcessingException {
        String payloadJson = objectMapper.writeValueAsString(dto.getPayload());
        AccountEventEntity entity = mappingService.toEntity(dto, payloadJson);
        accountRepo.save(entity);
    }
}