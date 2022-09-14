package net.diegoqueres.astronews.domain.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Collections;
import java.util.List;

public class PageableUtils<T> {

    public Page<T> listToPage(List<T> list, Pageable pageable) {
        final var start = (int) pageable.getOffset();
        final var end = Math.min((start + pageable.getPageSize()), list.size());

        if (start > end)
            return new PageImpl<>(Collections.emptyList(), pageable, list.size());

        var pageList = list.subList(start, end);
        var page = new PageImpl<>(pageList, pageable, list.size());
        return page;
    }

}
