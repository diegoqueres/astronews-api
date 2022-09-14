package net.diegoqueres.astronews.domain.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Configuration
@ConfigurationProperties("news-api-gateway")
public class NewsApiGatewayConfig {
    private String baseUrl;
    private String apiKey; 
    private String everythingEndpoint;

    private final Map<String, String> keywordByLang = new HashMap<>();
    public NewsApiGatewayConfig() {
        keywordByLang.put("en", "astronomy");
        keywordByLang.put("pt", "astronomia");
    }
}
