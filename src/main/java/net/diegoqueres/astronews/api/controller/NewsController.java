package net.diegoqueres.astronews.api.controller;

import net.diegoqueres.astronews.api.dto.LanguageDTO;
import net.diegoqueres.astronews.domain.dto.NewsDTO;
import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsDTO;
import net.diegoqueres.astronews.domain.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("/api/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    @GetMapping("/latest")
    public ResponseEntity<Page<NewsDTO>> getLatestNews(
            @RequestParam(required = false, defaultValue = "en") String language,
            Pageable pageable) {
        if (!validate(language))
            return ResponseEntity.badRequest().build();

        var page = newsService.getLatestNews(language, pageable);
        return ResponseEntity.ok(page);
    }

    @GetMapping("/supported-languages")
    public List<LanguageDTO> getAllSupportedLanguages() {
        return List.of(
                new LanguageDTO("English", "en"),
                new LanguageDTO("Portuguese", 	"pt"),
                new LanguageDTO("Spanish", 	"es")
        );
    }

    private boolean validate(String language) {
        return getAllSupportedLanguages().stream().anyMatch(lang -> lang.getCode().equalsIgnoreCase(language));
    }

}
