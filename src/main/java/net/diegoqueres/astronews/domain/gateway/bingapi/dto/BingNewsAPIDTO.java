package net.diegoqueres.astronews.domain.gateway.bingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingNewsAPIDTO {
    private Integer totalEstimatedMatches;
    private List<BingNewsDTO> value = new ArrayList<>();
}
