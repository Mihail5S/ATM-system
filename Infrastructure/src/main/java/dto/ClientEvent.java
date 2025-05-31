package dto;

import java.time.Instant;
import java.util.Map;

public class ClientEvent {
    private String id;
    private String actionType;
    private Map<String,Object> payload;
    private Instant timestamp;

    public ClientEvent() {}

    public ClientEvent(String id, String actionType, Map<String,Object> payload, Instant timestamp) {
        this.id = id;
        this.actionType = actionType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

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