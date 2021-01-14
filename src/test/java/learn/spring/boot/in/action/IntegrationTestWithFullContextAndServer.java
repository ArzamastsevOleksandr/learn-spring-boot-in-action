package learn.spring.boot.in.action;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

// MinimalIntegrationTestWithNoServer      time taken: 367475291
// IntegrationTestWithFullContextAndServer time taken: 3344633719
@ActiveProfiles("test")
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = { // overrides the .yml properties
                "app.prop_01=cc"
        })
class IntegrationTestWithFullContextAndServer {

    static long start;

    @Autowired
    ApplicationContext applicationContext;

    @Value("${app.prop_01}")
    String prop_01;
    @Value("${app.prop_02}")
    String prop_02;

    @Value("${app.prop_07}")
    String prop_07;

    // SB will set the local.server.port for a randomly generated port
    @Value("${local.server.port}")
    String port;

    @BeforeAll
    static void beforeAll() {
        start = System.nanoTime();
    }

    @Test
    void contextLoads() {
        assertThat(prop_01).isEqualTo("cc");
        assertThat(prop_02).isEqualTo("bbbbb");
        assertThatNoException().isThrownBy(() -> UUID.fromString(prop_07));

        assertTrue(applicationContext.getEnvironment().containsProperty("server.port"));

        assertThat(applicationContext.getBean("amazonProperties")).isNotNull();
        assertThat(applicationContext.getBean("testProfileBean")).isNotNull();
        assertThat(applicationContext.getBean("bookController")).isNotNull();

        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.err::println);

    }

    @Test
    void pageNotFound() {
        var restTemplate = new RestTemplate();
        assertThatThrownBy(() -> restTemplate.getForObject("http://localhost:{port}/no/way", String.class, port))
                .isInstanceOf(HttpClientErrorException.class)
                .hasFieldOrPropertyWithValue("statusCode", HttpStatus.NOT_FOUND);
    }

    @AfterAll
    static void afterAll() {
        System.err.println(MinimalIntegrationTestWithNoServer.class.getName() + " taken to execute (ns): " + (System.nanoTime() - start));
    }

}
