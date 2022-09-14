package net.diegoqueres.astronews.domain.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("bing-api-gateway")
public class BingApiGatewayConfig {
    private String baseUrl;
    private String apiKey;
    private String newsEndpoint;
}
