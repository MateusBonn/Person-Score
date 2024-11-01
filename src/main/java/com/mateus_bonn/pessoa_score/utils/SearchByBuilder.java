package com.mateus_bonn.pessoa_score.utils;

import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;


public final class SearchByBuilder {

    private SearchByBuilder() {

    }

    public static QueryHolder buildGenericSearchBy(final String search,
                                                   final List<String> searchBy,
                                                   final Map<String, String> mapColumn) {
        return buildGenericSearchBy(search, searchBy, mapColumn, true);
    }

    public static QueryHolder buildGenericSearchBy(final String search,
                                                   final List<String> searchBy,
                                                   final Map<String, String> mapColumn,
                                                   final boolean startWithAnd) {
        StringBuilder sql = new StringBuilder();
        Map<String, Object> mapParams = new HashMap<>();
        List<String> filteredSearchBy = Optional.ofNullable(searchBy)
                .map(list -> list.stream().filter(mapColumn::containsKey).collect(Collectors.toList()))
                .orElse(Collections.emptyList());
        int index = 0;

        if (StringUtils.hasText(search) && !CollectionUtils.isEmpty(filteredSearchBy)) {
            sql.append(startWithAnd ? " AND (" : " (");
            for (String column : filteredSearchBy) {
                boolean isLastColumn = filteredSearchBy.size() - index == 1;

                sql.append("upper(")
                        .append(mapColumn.get(column))
                        .append(") like concat ('%', upper(:search), '%')")
                        .append(isLastColumn ? " ) " : " or ");
                index++;
            }
            mapParams.put("search", search);
        }

        return new QueryHolder(sql.toString(), mapParams);
    }
}
