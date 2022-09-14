package net.diegoqueres.astronews.domain.gateway.bingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingNewsDTO {
    private String name;
    private String url;
    private BingNewsImgDTO image;
    private String description;
    private List<BingNewsProviderDTO> provider = new ArrayList<>();
    private String category;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime datePublished;

}
