package learn.spring.boot.in.action;

import learn.spring.boot.in.action.config.AmazonProperties;
import learn.spring.boot.in.action.config.TestProfileBean;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

// MinimalIntegrationTest time taken:         367912781
// IntegrationTestWithFullContext time taken: 2977723422
@ActiveProfiles("test")
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {
        AmazonProperties.class,
        TestProfileBean.class
})
class MinimalIntegrationTest {

    static long start;

    @Autowired
    ApplicationContext applicationContext;

    @BeforeAll
    static void beforeAll() {
        start = System.nanoTime();
    }

    @Test
    void name() {
        assertThat(applicationContext.getBean("amazonProperties")).isNotNull();
        assertThat(applicationContext.getBean("testProfileBean")).isNotNull();

        assertThatThrownBy(() -> applicationContext.getBean("bookController"))
                .isInstanceOf(NoSuchBeanDefinitionException.class);

        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(System.err::println);
    }

    @AfterAll
    static void afterAll() {
        System.err.println(MinimalIntegrationTest.class.getName() + " taken to execute (ns): " + (System.nanoTime() - start));
    }

}
