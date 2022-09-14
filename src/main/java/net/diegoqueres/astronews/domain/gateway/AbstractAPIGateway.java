package net.diegoqueres.astronews.domain.gateway;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import okhttp3.OkHttpClient;

import java.time.format.DateTimeFormatter;
import java.util.Map;

public abstract class AbstractAPIGateway {
    protected final static Map<String, String> keywordByLang = Map.of(
            "en", "astronomy",
            "en-gb", "astronomy",
            "pt", "astronomia",
            "pt-br", "astronomia",
            "es", "astronomia"
    );
    protected final DateTimeFormatter formatter;
    protected final OkHttpClient client;
    protected final ObjectMapper objectMapper;

    public AbstractAPIGateway() {
        this.formatter = DateTimeFormatter.ISO_DATE;
        this.client = new OkHttpClient();
        this.objectMapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
    }
}
