package io.github.kattlo.piemok.micronaut;

import static com.ericsson.commonlibrary.proxy.Proxy.with;

import java.util.Optional;

import javax.inject.Singleton;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.MockProducer;

import io.github.kattlo.piemok.MockedProducer;
import io.micronaut.configuration.kafka.KafkaProducerFactory;
import io.micronaut.configuration.kafka.config.AbstractKafkaProducerConfiguration;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Parameter;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.context.annotation.Replaces;

/**
 * @author fabiojose
 */
@Factory
@Singleton
public class MockedKafkaProducerFactory extends KafkaProducerFactory {

    private MockProducer<Object, Object> real;
    private KafkaProducer<Object, Object> producer;

    @Override
    @Replaces(KafkaProducer.class)
    @Prototype
    @SuppressWarnings("unchecked")
    public <K, V> KafkaProducer<K, V> createProducer(
        @Parameter
        AbstractKafkaProducerConfiguration<K, V> producerConfiguration) {

        if(null== producer) {

            real = MockedProducer.create();
            producer = with(KafkaProducer.class).delegate(real).get(); 

        }

        return (KafkaProducer<K, V>)producer;
    }
    
    @SuppressWarnings("unchecked")
    public <K, V> Optional<MockProducer<K, V>> producer() {
        return Optional.ofNullable((MockProducer<K, V>)real);
    }
}
