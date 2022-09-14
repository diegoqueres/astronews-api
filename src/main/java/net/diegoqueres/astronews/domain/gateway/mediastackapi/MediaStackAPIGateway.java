package net.diegoqueres.astronews.domain.gateway.mediastackapi;

import net.diegoqueres.astronews.domain.config.MediaStackApiGatewayConfig;
import net.diegoqueres.astronews.domain.exception.GatewayException;
import net.diegoqueres.astronews.domain.gateway.AbstractAPIGateway;
import net.diegoqueres.astronews.domain.gateway.mediastackapi.dto.MediaStackAPIDTO;
import net.diegoqueres.astronews.domain.gateway.newsapi.dto.NewsAPIDTO;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class MediaStackAPIGateway extends AbstractAPIGateway {

    @Autowired
    private MediaStackApiGatewayConfig config;

    public MediaStackAPIGateway() {
        super();
    }

    public MediaStackAPIDTO fetchNews(LocalDate from, String language) {
        try {
            var url = new StringBuilder();
            url.append(config.getBaseUrl())
               .append(config.getNewsEndpoint());
            url.append("?q=").append(keywordByLang.get(language));
            url.append("&sort=published_desc");
            url.append("&access_key=").append(config.getApiKey());
            url.append("&languages=").append(language);
            url.append("&offset=").append(0);
            url.append("&limit=").append(10);

            var request = new Request.Builder()
                .url(url.toString())
                .get()
                .build();
            
            var response = client.newCall(request).execute();
            return objectMapper.readValue(response.body().string(), MediaStackAPIDTO.class);
        } catch (Exception e) {
            throw new GatewayException(e);
        }
    }

}
