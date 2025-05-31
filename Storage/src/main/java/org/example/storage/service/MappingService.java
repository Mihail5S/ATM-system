package org.example.storage.service;

import org.example.storage.dto.ClientEventDto;
import org.example.storage.dto.AccountEventDto;
import org.example.storage.model.ClientEventEntity;
import org.example.storage.model.AccountEventEntity;
import org.springframework.stereotype.Service;

@Service
public class MappingService {

    public ClientEventEntity toEntity(ClientEventDto dto, String payloadJson) {
        ClientEventEntity entity = new ClientEventEntity();
        entity.setClientId(dto.getId());
        entity.setActionType(dto.getActionType());
        entity.setPayload(payloadJson);
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }

    public AccountEventEntity toEntity(AccountEventDto dto, String payloadJson) {
        AccountEventEntity entity = new AccountEventEntity();
        entity.setAccountId(dto.getId());
        entity.setActionType(dto.getActionType());
        entity.setPayload(payloadJson);
        entity.setTimestamp(dto.getTimestamp());
        return entity;
    }
}

