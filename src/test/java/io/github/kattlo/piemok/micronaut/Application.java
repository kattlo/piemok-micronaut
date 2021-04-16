package io.github.kattlo.piemok.micronaut;

import java.util.HashMap;
import java.util.Map;

import io.micronaut.configuration.kafka.annotation.KafkaListener;
import io.micronaut.configuration.kafka.annotation.Topic;
import io.micronaut.messaging.annotation.MessageBody;
import io.micronaut.runtime.Micronaut;

@KafkaListener(groupId = "micronaut")
public class Application {

    static final Map<String, String> STORE = new HashMap<>();

    public static void main(String[] args) {
        Micronaut.run(Application.class, args);
    }

    @Topic("test")
    public void receive(@MessageBody String value) {
        STORE.put("test", value);

        System.out.println(" >>> " + value);

    }
}
