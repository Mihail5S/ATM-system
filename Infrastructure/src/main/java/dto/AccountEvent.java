package dto;

import java.time.Instant;
import java.util.Map;

public class AccountEvent {
    private String id;
    private String actionType;
    private Map<String,Object> payload;
    private Instant timestamp;

    public AccountEvent(String id, String actionType, Map<String,Object> payload, Instant timestamp) {
        this.id = id;
        this.actionType = actionType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }
    public String getActionType() {
        return actionType;
    }
    public Map<String, Object> getPayload() {
        return payload;
    }
    public Instant getTimestamp() {
        return timestamp;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    public void setPayload(Map<String, Object> payload) {
        this.payload = payload;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
