package event;

import dto.AccountEvent;
import dto.ClientEvent;
import org.springframework.stereotype.Component;

@Component
public interface EventPublisher {
    void publishClientEvent(ClientEvent event);
    void publishAccountEvent(AccountEvent event);
}
