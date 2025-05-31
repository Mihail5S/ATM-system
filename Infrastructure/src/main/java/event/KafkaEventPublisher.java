package event;

import dto.AccountEvent;
import dto.ClientEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class KafkaEventPublisher implements EventPublisher {

    private final KafkaTemplate<String,Object> kafka;
    private final String clientTopic;
    private final String accountTopic;

    public KafkaEventPublisher(KafkaTemplate<String,Object> kafka,
                               @Value("${storage.topic.client:client-topic}") String clientTopic,
                               @Value("${storage.topic.account:account-topic}") String accountTopic) {
        this.kafka       = kafka;
        this.clientTopic = clientTopic;
        this.accountTopic= accountTopic;
    }

    @Override
    public void publishClientEvent(ClientEvent event) {
        kafka.send(clientTopic, event.getId(), event);
    }

    @Override
    public void publishAccountEvent(AccountEvent event) {
        kafka.send(accountTopic, event.getId(), event);
    }
}

