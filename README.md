## 🥧 Piemok Micronaut

Convenience lib to use Piemok within Micronaut projects.

- [See Piemok](https://github.com/kattlo/piemok)

__Support:__

- Java 11+
- Apache Kafka® 2.6.0+
- Micronaut
- Consuming by Subscription

## Getting Started

1. Dependency

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
	    testImplementation 'com.github.kattlo:piemok-micronaut:v0.1.0'
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
	    <version>v0.1.0</version>
	</dependency>
    ```

  - [See other options](https://jitpack.io/#kattlo/piemok-micronaut)