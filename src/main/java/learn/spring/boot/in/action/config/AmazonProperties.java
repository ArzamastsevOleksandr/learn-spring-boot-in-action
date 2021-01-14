package learn.spring.boot.in.action.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "amazon")
public class AmazonProperties {

    private String associateId;
    private String productUrl;

}
