package net.diegoqueres.astronews.domain.gateway.newsapi;

import net.diegoqueres.astronews.domain.config.NewsApiGatewayConfig;
import net.diegoqueres.astronews.domain.exception.GatewayException;
import net.diegoqueres.astronews.domain.gateway.AbstractAPIGateway;
import net.diegoqueres.astronews.domain.gateway.newsapi.dto.NewsAPIDTO;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class NewsAPIGateway extends AbstractAPIGateway {

    @Autowired
    private NewsApiGatewayConfig config;

    public NewsAPIGateway() {
        super();
    }

    public NewsAPIDTO fetchNews(LocalDate from, String language) {
        try {
            var url = new StringBuilder();
            url.append(config.getBaseUrl())
               .append(config.getEverythingEndpoint());
            url.append("?q=").append(config.getKeywordByLang().get(language));
            url.append("&from=").append(from.format(formatter));
            url.append("&sortBy=popularity");
            url.append("&apiKey=").append(config.getApiKey());
            url.append("&language=").append(language);

            var request = new Request.Builder()
                .url(url.toString())
                .get()
                .build();
            
            var response = client.newCall(request).execute();
            return objectMapper.readValue(response.body().string(), NewsAPIDTO.class);
        } catch (Exception e) {
            throw new GatewayException(e);
        }
    }

}
