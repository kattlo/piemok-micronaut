package io.github.kattlo.piemok.micronaut;

import io.micronaut.runtime.EmbeddedApplication;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;

import javax.inject.Inject;

@MicronautTest
class MockedKafkaConsumerFactoryTest {

    @Inject
    EmbeddedApplication<?> application;

    @Inject
    MockedKafkaConsumerFactory factory;

    @Test
    void testItWorks() {
        Assertions.assertTrue(application.isRunning());
    }

    @Test
    void should_return_the_mocked_consumer() {

        var consumer = factory.consumerOf("micronaut");

        assertTrue(consumer.isPresent());
    }

    @Test
    void should_run_against_the_mocked_consumer() throws Exception {

        var consumer = factory.consumerOf("micronaut");
        var expected = "A Value";

        consumer.ifPresent(c -> {
            c.reset("test", null, expected);
        });

        /* Tip: perform some sleep and the listener will have time to consume
         * and process
         */
        Thread.sleep(Duration.ofSeconds(3).toMillis());

        var actual = Application.STORE.get("test");
        assertEquals(expected, actual);
    }

}
