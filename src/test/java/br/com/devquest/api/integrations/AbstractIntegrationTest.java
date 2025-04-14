package br.com.devquest.api.integrations;

import org.flywaydb.core.Flyway;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.Map;
import java.util.stream.Stream;

@ContextConfiguration(initializers = AbstractIntegrationTest.Initializer.class)
public class AbstractIntegrationTest {

  static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15")
            .withDatabaseName("testdb")
            .withUsername("testuser")
            .withPassword("testpass");

    private static void startContainers() {
      Startables.deepStart(Stream.of(postgres)).join();
      runFlywayMigrations();
    }

    private static void runFlywayMigrations() {
      Flyway flyway = Flyway.configure()
              .dataSource(postgres.getJdbcUrl(), postgres.getUsername(), postgres.getPassword())
              .load();
      flyway.migrate();
    }

    private static Map<String, String> createConnectionConfiguration() {
      return Map.of(
              "spring.datasource.url", postgres.getJdbcUrl(),
              "spring.datasource.username", postgres.getUsername(),
              "spring.datasource.password", postgres.getPassword()
      );
    }

    @Override
    public void initialize(ConfigurableApplicationContext applicationContext) {
      startContainers();
      ConfigurableEnvironment environment = applicationContext.getEnvironment();
      MapPropertySource testcontainers = new MapPropertySource(
              "testcontainers",
              (Map) createConnectionConfiguration());
      environment.getPropertySources().addFirst(testcontainers);
    }
  }

  protected static void resetDatabase() {
    Flyway flyway = Flyway.configure()
      .dataSource(
        Initializer.postgres.getJdbcUrl(),
        Initializer.postgres.getUsername(),
        Initializer.postgres.getPassword()
      )
      .cleanDisabled(false)
      .load();

    flyway.clean();
    flyway.migrate();
  }

}
