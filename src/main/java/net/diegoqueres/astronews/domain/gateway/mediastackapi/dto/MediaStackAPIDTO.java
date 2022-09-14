package net.diegoqueres.astronews.domain.gateway.mediastackapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaStackAPIDTO {
    private List<MediaStackNewsDTO> articles = new ArrayList<>();
}
