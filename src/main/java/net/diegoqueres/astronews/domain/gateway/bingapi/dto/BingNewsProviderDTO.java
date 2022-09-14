package net.diegoqueres.astronews.domain.gateway.bingapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BingNewsProviderDTO {
    private String name;
}
