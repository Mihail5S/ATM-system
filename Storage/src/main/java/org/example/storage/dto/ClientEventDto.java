package org.example.storage.dto;

import java.time.Instant;
import java.util.Map;

public class ClientEventDto {
    private String id;
    private String actionType;
    private Map<String, Object> payload;
    private Instant timestamp;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getActionType() {
        return actionType;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    public Map<String, Object> getPayload() {
        return payload;
    }
    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }


}
