package org.example.storage.service;

import org.example.storage.dto.AccountEventDto;
import org.example.storage.dto.ClientEventDto;
import org.example.storage.model.AccountEventEntity;
import org.example.storage.model.ClientEventEntity;
import org.example.storage.repository.AccountEventRepository;
import org.example.storage.repository.ClientEventRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.stereotype.Service;

@Service
public class EventStorageService {


    private ClientEventRepository clientRepo;


    private AccountEventRepository accountRepo;


    private MappingService mappingService;


    private ObjectMapper objectMapper;

    public EventStorageService(ClientEventRepository clientRepo, AccountEventRepository accountRepo, MappingService mappingService, ObjectMapper objectMapper) {
        this.clientRepo = clientRepo;
        this.accountRepo = accountRepo;
        this.mappingService = mappingService;
        this.objectMapper = objectMapper;
    }

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