package org.example.storage.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "client_events")
public class ClientEventEntity {
    @Id
    @Column(name = "event_id", nullable = false, updatable = false)
    private String eventId;

    @Column(name = "client_id", nullable = false)
    private String clientId;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(columnDefinition = "text")
    private String payload;

    @Column(nullable = false)
    private Instant timestamp;

    public ClientEventEntity() {
        this.eventId = UUID.randomUUID().toString();
    }

    public ClientEventEntity(String clientId,
                             String actionType,
                             String payload,
                             Instant timestamp) {
        this.eventId = UUID.randomUUID().toString();
        this.clientId = clientId;
        this.actionType = actionType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }
    public String getClientId() {
        return clientId;
    }
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
    public String getActionType() {
        return actionType;
    }
    public void setActionType(String actionType) {
        this.actionType = actionType;
    }
    public String getPayload() {
        return payload;
    }
    public void setPayload(String payload) {
        this.payload = payload;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
}
