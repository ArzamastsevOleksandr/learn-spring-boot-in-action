package learn.spring.boot.in.action.config;

import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

@Slf4j
@Component
@ConfigurationProperties(prefix = "app")
@Setter
public class AppProperties {

    private int prop_01;
    private int prop_02;
    private int prop_03;
    private long prop_04;
    private long prop_05;
    private String prop_06;
    private UUID prop_07;

    @PostConstruct
    public void postConstruct() {
        log.error("{}: {}", this.getClass().getName(), ReflectionToStringBuilder.toString(this, ToStringStyle.MULTI_LINE_STYLE));
    }

}
