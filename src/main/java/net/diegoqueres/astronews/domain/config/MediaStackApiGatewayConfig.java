package net.diegoqueres.astronews.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("media-stack-gateway")
public class MediaStackApiGatewayConfig {
    private String baseUrl;
    private String apiKey;
    private String newsEndpoint;
}
