package net.diegoqueres.astronews.domain.service;

import lombok.extern.slf4j.Slf4j;
import net.diegoqueres.astronews.domain.dto.NewsDTO;
import net.diegoqueres.astronews.domain.gateway.bingapi.BingAPIGateway;
import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsAPIDTO;
import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsDTO;
import net.diegoqueres.astronews.domain.gateway.mediastackapi.MediaStackAPIGateway;
import net.diegoqueres.astronews.domain.gateway.mediastackapi.dto.MediaStackAPIDTO;
import net.diegoqueres.astronews.domain.gateway.newsapi.NewsAPIGateway;
import net.diegoqueres.astronews.domain.gateway.newsapi.dto.NewsAPIDTO;
import net.diegoqueres.astronews.domain.gateway.newsapi.dto.NewsEntryDTO;
import net.diegoqueres.astronews.domain.utils.PageableUtils;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

@Slf4j
@Service
public class NewsService {

    @Value("${latest-news.days-interval}")
    private Long daysInterval;

    @Autowired
    private ModelMapper gatewayModelMapper;

    @Autowired
    private BingAPIGateway bingNewsAPIGateway;

    @Autowired
    private MediaStackAPIGateway mediaStackAPIGateway;

    @Autowired
    private NewsAPIGateway newsAPIGateway;

    private PageableUtils<NewsDTO> pageableUtils;
    private LevenshteinDistance levenshteinDistance;

    public NewsService() {
        pageableUtils = new PageableUtils<>();
        levenshteinDistance = new LevenshteinDistance();
    }

    public List<BingNewsDTO> getNewsFromBingAPI(LocalDate from, String language) {
        BingNewsAPIDTO newsAPIResponse = bingNewsAPIGateway.fetchNews(from, language);
        List<BingNewsDTO> articles = newsAPIResponse.getValue();

        articles = articles.stream()
                .filter(result -> isNull(result.getCategory()) || result.getCategory().equalsIgnoreCase("ScienceAndTechnology"))
                .collect(Collectors.toList());

        return articles;
    }

    public Page<NewsDTO> getLatestNews(String language, Pageable pageable) {
        List<NewsDTO> latestNews = fetchNews(language);
        return pageableUtils.listToPage(latestNews, pageable);
    }

    @Cacheable(cacheNames = "latestNews", key = "#language")
    private List<NewsDTO> fetchNews(String language) {
        final var startDate = LocalDate.now().minusDays(daysInterval);

        var latestMediaStackNews = fetchMediaStackNews(startDate, language);
        var latestBingNews = fetchBingNews(startDate, language);

        var news = new ArrayList<NewsDTO>();
        copyNonDuplicatedNews(latestMediaStackNews, news);
        copyNonDuplicatedNews(latestBingNews, news);
        news.sort(Comparator.comparing(NewsDTO::getPublishedAt).reversed());
        return news;
    }

    private List<NewsDTO> fetchBingNews(LocalDate from, String language) {
        BingNewsAPIDTO newsAPIResponse = bingNewsAPIGateway.fetchNews(from, language);
        return newsAPIResponse.getValue().stream()
                .filter(result -> isNull(result.getCategory()) || result.getCategory().equalsIgnoreCase("ScienceAndTechnology"))
                .map(el -> gatewayModelMapper.map(el, NewsDTO.class))
                .collect(Collectors.toList());
    }

    private List<NewsDTO> fetchMediaStackNews(LocalDate from, String language) {
        MediaStackAPIDTO newsAPIResponse = mediaStackAPIGateway.fetchNews(from, language);
        return newsAPIResponse.getArticles().stream()
                .map(el -> gatewayModelMapper.map(el, NewsDTO.class))
                .collect(Collectors.toList());
    }

    private void copyNonDuplicatedNews(List<NewsDTO> source, List<NewsDTO> destiny) {
        var entriesToCopy = new ArrayList<NewsDTO>();

        for (var i = 0; i < source.size(); i++) {
            var hasDuplicate = false;
            for (var j = 0; j < destiny.size() && !hasDuplicate; j++) {
                if (levenshteinDistance.apply(source.get(i).getTitle(), destiny.get(i).getTitle()) < 7)
                    hasDuplicate = true;
            }
            if (!hasDuplicate)
                entriesToCopy.add(source.get(i));
        }

        destiny.addAll(entriesToCopy);
    }

    /**
        DON'T USE in production environment (only if you subscribe News API Service).
     */
    public List<NewsEntryDTO> fetchNewsAPI(LocalDate from, String language) {
        NewsAPIDTO newsAPIResponse = newsAPIGateway.fetchNews(from, language);
        List<NewsEntryDTO> articles = newsAPIResponse.getArticles();
        return articles;
    }

}
