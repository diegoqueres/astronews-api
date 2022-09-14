package net.diegoqueres.astronews.domain.config;

import net.diegoqueres.astronews.domain.dto.NewsDTO;
import net.diegoqueres.astronews.domain.config.converters.BingProviderListConverter;
import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiGatewayConfig {

    @Bean
    public ModelMapper gatewayModelMapper() {
        ModelMapper modelMapper = new ModelMapper();

        TypeMap<BingNewsDTO, NewsDTO> bingTypeMap = modelMapper.createTypeMap(BingNewsDTO.class, NewsDTO.class);
        bingTypeMap.addMapping(BingNewsDTO::getName, NewsDTO::setTitle);
        bingTypeMap.addMapping((bingNewsDTO -> bingNewsDTO.getImage().getContentUrl()), NewsDTO::setImage);

        bingTypeMap.addMappings(mapper -> mapper.using(new BingProviderListConverter())
                .map(BingNewsDTO::getProvider, NewsDTO::setSource));

        bingTypeMap.addMapping(BingNewsDTO::getDatePublished, NewsDTO::setPublishedAt);

        return modelMapper;
    }

}
