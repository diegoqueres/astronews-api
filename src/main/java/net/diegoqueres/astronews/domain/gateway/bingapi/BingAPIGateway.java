package net.diegoqueres.astronews.domain.gateway.bingapi;

import net.diegoqueres.astronews.domain.config.BingApiGatewayConfig;
import net.diegoqueres.astronews.domain.exception.GatewayException;
import net.diegoqueres.astronews.domain.gateway.AbstractAPIGateway;
import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsAPIDTO;
import okhttp3.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.LocalDate;

@Component
public class BingAPIGateway extends AbstractAPIGateway {

    @Autowired
    private BingApiGatewayConfig config;

    public BingAPIGateway() {
        super();
    }

    public BingNewsAPIDTO fetchNews(LocalDate from, String language) {
        try {
            language = language.toLowerCase();
            var keyword = keywordByLang.get(language);
            var since =  from.toEpochDay();

            var url = new StringBuilder();
            url.append(config.getBaseUrl())
               .append(config.getNewsEndpoint());
            url.append("?q=").append(keyword);
            url.append("&since=").append(since);
            url.append("&sortBy=date");
            url.append("&setLang=").append(language);
            url.append("&originalImg=true");
            url.append("&safeSearch=Strict");
            url.append("&count=25");
            url.append("&offset=0");

            var request = new Request.Builder()
                .header("Ocp-Apim-Subscription-Key", config.getApiKey())
                .url(url.toString())
                .get()
                .build();
            
            var response = client.newCall(request).execute();

            return objectMapper.readValue(response.body().string(), BingNewsAPIDTO.class);
        } catch (Exception e) {
            throw new GatewayException(e);
        }
    }

}
