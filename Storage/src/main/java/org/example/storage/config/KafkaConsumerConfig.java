package org.example.storage.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.example.storage.dto.ClientEventDto;
import org.example.storage.dto.AccountEventDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.kafka.consumer.group-id}")
    private String groupId;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ClientEventDto>
    clientKafkaListenerContainerFactory() {

        JsonDeserializer<ClientEventDto> valueDeserializer =
                new JsonDeserializer<>(ClientEventDto.class, false);

        valueDeserializer.setUseTypeHeaders(false);

        valueDeserializer.addTrustedPackages("org.example.storage.dto");


        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId + "-client");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                valueDeserializer.getClass());

        DefaultKafkaConsumerFactory<String, ClientEventDto> cf =
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        valueDeserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, ClientEventDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, AccountEventDto>
    accountKafkaListenerContainerFactory() {

        JsonDeserializer<AccountEventDto> valueDeserializer =
                new JsonDeserializer<>(AccountEventDto.class, false);
        valueDeserializer.setUseTypeHeaders(false);
        valueDeserializer.addTrustedPackages("org.example.storage.dto");

        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId + "-account");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
                valueDeserializer.getClass());

        DefaultKafkaConsumerFactory<String, AccountEventDto> cf =
                new DefaultKafkaConsumerFactory<>(
                        props,
                        new StringDeserializer(),
                        valueDeserializer
                );

        ConcurrentKafkaListenerContainerFactory<String, AccountEventDto> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(cf);
        return factory;
    }
}
