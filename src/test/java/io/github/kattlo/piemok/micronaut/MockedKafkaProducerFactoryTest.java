package io.github.kattlo.piemok.micronaut;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.inject.Inject;

import org.apache.kafka.clients.producer.MockProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

@MicronautTest
public class MockedKafkaProducerFactoryTest {
    
    @Inject
    EmbeddedApplication<?> application;

    @Inject
    MockedKafkaProducerFactory factory;

    @Inject
    ProducerExemplo producer;

    @BeforeEach
    void beforeEach() {
        factory.producer().ifPresent(MockProducer::clear);
    }

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void should_return_the_mocked_producer() {

        producer.send("Test 1");

        var actual = factory.producer();

        assertTrue(actual.isPresent());
    }

    @Test
    void should_run_against_the_mocked_producer() throws Exception {

        var expected = "Test 2";
        producer.send(expected);

        var records = factory.producer().get().history();
        assertEquals(1, records.size());

        var actual = records.iterator().next();
        assertEquals(expected, actual.value());
    }
}
