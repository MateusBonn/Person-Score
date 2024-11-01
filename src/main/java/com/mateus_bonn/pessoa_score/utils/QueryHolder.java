package com.mateus_bonn.pessoa_score.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;

@Setter
@Getter
public class QueryHolder {

    private String sqlQuery;
    private String filters;
    private List<Object> params;
    private Map<String, Object> mapParams;

    public QueryHolder() {
    }

    public QueryHolder(String sqlQuery, Map<String, Object> mapParams) {
        this.sqlQuery = sqlQuery;
        this.mapParams = mapParams;
    }

}
