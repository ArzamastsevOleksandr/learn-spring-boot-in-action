package learn.spring.boot.in.action;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Slf4j
@SpringBootApplication
public class SpringBootInActionApplication {

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
    }

    @PreDestroy
    public void preDestroy() {
        log.debug("@PostConstruct");
        log.info("@PostConstruct");
        log.warn("@PostConstruct");
        log.trace("@PostConstruct");
        log.error("@PostConstruct");
    }

}
