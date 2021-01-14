package learn.spring.boot.in.action;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNoException;

// MinimalIntegrationTest time taken:         367912781
// IntegrationTestWithFullContext time taken: 2977723422
@ActiveProfiles("test")
@SpringBootTest
class IntegrationTestWithFullContext {

    static long start;

    @Autowired
    ApplicationContext applicationContext;

    @Value("${app.prop_01}")
    String prop_01;
    @Value("${app.prop_02}")
    String prop_02;

    @Value("${app.prop_07}")
    String prop_07;

    @BeforeAll
    static void beforeAll() {
        start = System.nanoTime();
    }

    @Test
    void contextLoads() {
        assertThat(prop_01).isEqualTo("aaaaa");
        assertThat(prop_02).isEqualTo("bbbbb");

        assertThatNoException().isThrownBy(() -> UUID.fromString(prop_07));

        assertThat(applicationContext.getBean("amazonProperties")).isNotNull();
        assertThat(applicationContext.getBean("testProfileBean")).isNotNull();
        assertThat(applicationContext.getBean("bookController")).isNotNull();

        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.err::println);
    }

    @AfterAll
    static void afterAll() {
        System.err.println(MinimalIntegrationTest.class.getName() + " taken to execute (ns): " + (System.nanoTime() - start));
    }

}
