package io.github.kattlo.piemok.micronaut;

import io.micronaut.configuration.kafka.annotation.KafkaClient;
import io.micronaut.configuration.kafka.annotation.Topic;

@KafkaClient
public interface ProducerExemplo {
    
    @Topic("micronaut")
    void send(String value);

}
