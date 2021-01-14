package learn.spring.boot.in.action;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.Arrays;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
public class SpringBootInActionApplication {

    private final ApplicationContext applicationContext;

    public static void main(String[] args) {
        SpringApplication.run(SpringBootInActionApplication.class, args);
    }

    @PostConstruct
    public void postConstruct() {
        log.debug("@PostConstruct");
        log.info("@PostConstruct");
        log.error("@PostConstruct");
        log.warn("@PostConstruct");
        log.trace("@PostConstruct");
        log.debug("================================================================");
        Arrays.stream(applicationContext.getBeanDefinitionNames())
                .forEach(beanName -> log.error("BeanName: {}", beanName));
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("@PreDestroy");
        log.info("@PreDestroy");
        log.warn("@PreDestroy");
        log.trace("@PreDestroy");
        log.error("@PreDestroy");
    }

}
