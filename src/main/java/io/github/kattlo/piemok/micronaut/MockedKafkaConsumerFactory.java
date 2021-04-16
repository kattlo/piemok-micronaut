package io.github.kattlo.piemok.micronaut;

import static com.ericsson.commonlibrary.proxy.Proxy.with;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import javax.inject.Singleton;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import io.micronaut.configuration.kafka.KafkaConsumerFactory;
import io.micronaut.configuration.kafka.config.AbstractKafkaConsumerConfiguration;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Replaces;

import io.github.kattlo.piemok.MockedConsumer;

/**
 * @author fabiojose
 */
@Factory
@Singleton
public class MockedKafkaConsumerFactory extends KafkaConsumerFactory {
    
    private Map<String, MockedConsumer<Object, Object>> consumers = new HashMap<>();

    @Override
    @Replaces(KafkaConsumer.class)
    @Prototype
    @SuppressWarnings("unchecked")
    public <K, V> KafkaConsumer<K, V> createConsumer(
            @Parameter AbstractKafkaConsumerConfiguration<K, V> consumerConfiguration) {

        var config = consumerConfiguration.getConfig();
        var groupId = config.getProperty(ConsumerConfig.GROUP_ID_CONFIG);

        MockedConsumer<K, V> mocked = null; 

        if(!consumers.containsKey(groupId)) {

            mocked = MockedConsumer.forSubscribe();
            consumers.put(groupId, (MockedConsumer<Object, Object>)mocked);

        } else {
            throw new IllegalStateException("Consumer not found by group.id " + groupId);
        }

        return with(KafkaConsumer.class)
                .delegate(mocked.consumer())
                .get();
    }

    @SuppressWarnings("unchecked")
    public <K, V> Optional<MockedConsumer<K, V>> consumerOf(String groupId) {
        return Optional.ofNullable((MockedConsumer<K, V>)consumers.get(groupId));
    }
}
