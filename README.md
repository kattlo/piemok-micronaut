## 🥧 Piemok Micronaut

Convenience lib to use Piemok within Micronaut projects.

- [See Piemok](https://github.com/kattlo/piemok)

__Support:__

- Java 11+
- Apache Kafka® 2.6.0+
- Micronaut
- Consuming by Subscription

## Getting Started

### Dependency

  - Gradle
    ```groovy
    repositories {
        // ...

        maven {
            url = uri('http://packages.confluent.io/maven/')
        }

        maven { url 'https://jitpack.io' }
    }

    dependencies {
	    testImplementation("com.github.kattlo:piemok-micronaut:v0.4.0"){
            exclude group:'io.micronaut', module:'micronaut-bom'
        }
	}

    ```

  - Apache Maven®
    ```xml
    <repositories>
		<repository>
		    <id>confluent</id>
		    <url>http://packages.confluent.io/maven/</url>
		</repository>
		<repository>
		    <id>jitpack.io</id>
		    <url>https://jitpack.io</url>
		</repository>
	</repositories>

	<dependency>
        <scope>test</scope>
	    <groupId>com.github.kattlo</groupId>
	    <artifactId>piemok-micronaut</artifactId>
	    <version>v0.4.0</version>
        <exclusions>
          <exclusion>
            <groupId>io.micronaut</groupId>
            <artifactId>micronaut-bom</artifactId>
          </exclusion>
        </exclusions>
	</dependency>
    ```

  - [See other options](https://jitpack.io/#kattlo/piemok-micronaut)

### Usage with Java

__Consumer__

```java
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import org.junit.jupiter.api.Test;
import java.time.Duration;
import javax.inject.Inject;

import io.github.kattlo.piemok.micronaut.MockedKafkaConsumerFactory;

@MicronautTest
class MyTest {

    @Inject
    MockedKafkaConsumerFactory factory;

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

        // do your assertions
    }
}
```

__Producer__

```java
import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.inject.Inject;
import org.apache.kafka.clients.producer.MockProducer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;

import io.github.kattlo.piemok.micronaut.MockedKafkaProducerFactory;

@MicronautTest
public class MockedKafkaProducerFactoryTest {

    @Inject
    MockedKafkaProducerFactory factory;

    @Inject
    ProducerExemplo producer;

    @BeforeEach
    void beforeEach() {
        factory.producer().ifPresent(MockProducer::clear);
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
```

### Usage with Kotlin

```kotlin

```
