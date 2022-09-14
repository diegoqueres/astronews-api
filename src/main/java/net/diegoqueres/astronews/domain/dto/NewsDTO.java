package net.diegoqueres.astronews.domain.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serial;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Getter
@Setter
public class NewsDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1381686898566151066L;

    private String title;
    private String description;
    private String url;
    private String image;
    private String source;
    private String author;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private OffsetDateTime publishedAt;
}
