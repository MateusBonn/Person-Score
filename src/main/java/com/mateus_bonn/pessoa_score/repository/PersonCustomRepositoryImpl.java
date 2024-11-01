package com.mateus_bonn.pessoa_score.repository;

import com.mateus_bonn.pessoa_score.service.person.PersonFilter;
import com.mateus_bonn.pessoa_score.utils.OrderByAndPaginationBuilder;
import com.mateus_bonn.pessoa_score.utils.PageFilter;
import com.mateus_bonn.pessoa_score.utils.QueryHolder;
import com.mateus_bonn.pessoa_score.utils.SearchByBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class PersonCustomRepositoryImpl implements PersonCustomRepository{

    final NamedParameterJdbcTemplate jdbcTemplate;

    private Map<String, String> personDictionary() {
        return Map.ofEntries(
                Map.entry("age", "person.age"),
                Map.entry("name", "person.name"),
                Map.entry("cep", "person.cep"));
    }

    @Override
    public List<PersonFilter> findAll(PageFilter pageFilter, List<String> searchBy) {
        pageFilter.setOrderBy("person." + pageFilter.getOrderBy());
        QueryHolder queryHolderSearch = SearchByBuilder.buildGenericSearchBy(pageFilter.getSearch(), searchBy, personDictionary());

        String sql = select() +
                sql() +
                queryHolderSearch.getSqlQuery() +
                OrderByAndPaginationBuilder.buildSortAndPagination(pageFilter.toPageable());




        return jdbcTemplate.query(sql,
                bindFilterFindAllWithSearch(queryHolderSearch.getMapParams()),
                BeanPropertyRowMapper.newInstance(PersonFilter.class));
    }

    public long count(PageFilter pageFilter, List<String> searchBy) {
        QueryHolder queryHolderSearch = SearchByBuilder.buildGenericSearchBy(pageFilter.getSearch(), searchBy, personDictionary());


        String sql = "SELECT COUNT(*) " +
                sql() +
                queryHolderSearch.getSqlQuery();

        return jdbcTemplate.queryForObject(sql, bindFilterFindAllWithSearch(queryHolderSearch.getMapParams()), Long.class);
    }

    private MapSqlParameterSource bindFilterFindAllWithSearch(Map<String, Object> mapParams) {
        var parameters = new MapSqlParameterSource();

        for (Map.Entry<String, Object> entry : mapParams.entrySet()) {
            parameters = parameters.addValue(entry.getKey(), entry.getValue());
        }

        return parameters;
    }

    private String sql() {
        return from()
                + join()
                + where();
    }

    private String select() {
        return " select " +
                " person.id as id, " +
                " person.name as name, " +
                " person.age as age, " +
                " person.phone as phone, " +
                " person.cep as cep, " +
                " person.state as state, " +
                " person.city as city, " +
                " person.neighborhood as neighborhood, " +
                " person.street_address as streetAddress, " +
                " score.score_description as scoreDescription ";
    }

    private String from() {
        return " from person person";
    }

    private String join() {

        return " join score_definition score" +
                " on  person.score BETWEEN score.score_initial AND score.score_final";
    }

    private String where() {
        return " where person.score is not null";
    }
}
