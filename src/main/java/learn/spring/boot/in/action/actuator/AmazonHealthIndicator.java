package learn.spring.boot.in.action.actuator;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class AmazonHealthIndicator implements HealthIndicator {

    private final RestTemplate restTemplate;

    @Override
    public Health health() {
        try {
            restTemplate.getForObject("http://www.amazon.com", String.class);
            return Health.up().build();
        } catch (Exception e) {
            return Health.down().withDetail("reason", e.getMessage()).build();
        }
    }

}
