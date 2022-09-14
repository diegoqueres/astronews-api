package net.diegoqueres.astronews.domain.gateway.newsapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewsAPIDTO {
    private String status;
    private Integer totalResults;
    private List<NewsEntryDTO> articles = new ArrayList<>();
}
