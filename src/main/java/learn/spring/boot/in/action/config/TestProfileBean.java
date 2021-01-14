package learn.spring.boot.in.action.config;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("test")
@Component
public class TestProfileBean {
}
