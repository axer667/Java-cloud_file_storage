package ru.netology.cloudstorage.integration;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.containers.Network;
import ru.netology.cloudstorage.CloudStorageApplication;

import java.util.Map;

/*
@SpringBootTest(classes = CloudStorageApplication.class,webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
public class ContainerTest {

    private static final int PORT_DB = 5432;
    private static final int PORT_SERVER = 8000;

    private static final String DATABASE_NAME = "cloudservice";
    private static final String DATABASE_USERNAME = "postgres";
    private static final String DATABASE_PASSWORD = "postgres";

    private static final Network CLOUD_NETWORK = Network.newNetwork();

    @Container
    public static PostgreSQLContainer<?> databaseContainer = new PostgreSQLContainer<>("postgres")
            .withNetwork(CLOUD_NETWORK)
            .withExposedPorts(PORT_DB)
            .withDatabaseName(DATABASE_NAME)
            .withUsername(DATABASE_USERNAME)
            .withPassword(DATABASE_PASSWORD);
    @DynamicPropertySource
    private static void testPriperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url=", databaseContainer::getJdbcUrl);
        registry.add("spring.datasource.username=", databaseContainer::getUsername);
        registry.add("spring.datasource.password=", databaseContainer::getPassword);
        registry.add("spring.flyway.url=", databaseContainer::getJdbcUrl);
        registry.add("spring.flyway.user=", databaseContainer::getUsername);
        registry.add("spring.flyway.password=", databaseContainer::getPassword);
    }

    @Container
    public static GenericContainer<?> serverContainer = new GenericContainer<>("cloudservice_database")
            .withNetwork(CLOUD_NETWORK)
            .withExposedPorts(PORT_SERVER)
            .withEnv(Map.of("SPRING_DATASOURCE_URL", "jdbc:postgres://database:" + PORT_DB + "/" + DATABASE_NAME))
            .dependsOn(databaseContainer);

    @Test
    void contextDatabase() {
        Assertions.assertTrue(databaseContainer.isRunning());
    }

    @Test
    void contextServer() {
        Assertions.assertFalse(serverContainer.isRunning());
    }
}

 */