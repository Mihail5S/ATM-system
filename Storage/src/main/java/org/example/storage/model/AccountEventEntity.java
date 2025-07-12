package org.example.storage.model;

import jakarta.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "account_events")
public class AccountEventEntity {

    @Id
    @Column(name = "event_id", nullable = false, updatable = false)
    private String eventId;

    @Column(name = "account_id", nullable = false)
    private String accountId;

    @Column(name = "action_type", nullable = false)
    private String actionType;

    @Column(columnDefinition = "text")
    private String payload;

    @Column(nullable = false)
    private Instant timestamp;

    public AccountEventEntity() {
        this.eventId = UUID.randomUUID().toString();
    }

    public AccountEventEntity(String accountId,
                              String actionType,
                              String payload,
                              Instant timestamp) {
        this.eventId = UUID.randomUUID().toString();
        this.accountId = accountId;
        this.actionType = actionType;
        this.payload = payload;
        this.timestamp = timestamp;
    }

    public String getEventId() {
        return eventId;
    }
    public String getAccountId() {
        return accountId;
    }
    public void setAccountId(String accountId) {
        this.accountId = accountId;
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
