package net.diegoqueres.astronews.domain.gateway.mediastackapi.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.OffsetDateTime;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class MediaStackNewsDTO {
    private String title;
    private String description;
    private String url;
    private String image;
    private String source;
    private String author;
    private String category;
    private String language;
    private String country;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime publishedAt;
}
