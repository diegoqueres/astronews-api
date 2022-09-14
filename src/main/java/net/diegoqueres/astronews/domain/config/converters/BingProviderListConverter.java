package net.diegoqueres.astronews.domain.config.converters;

import net.diegoqueres.astronews.domain.gateway.bingapi.dto.BingNewsProviderDTO;
import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;

import java.util.List;
import java.util.stream.Collectors;

public class BingProviderListConverter implements Converter<List<BingNewsProviderDTO>, String> {

    @Override
    public String convert(MappingContext<List<BingNewsProviderDTO>, String> mappingContext) {
        return mappingContext.getSource().stream().map(p -> p.getName()).collect(Collectors.joining(", "));
    }

}
