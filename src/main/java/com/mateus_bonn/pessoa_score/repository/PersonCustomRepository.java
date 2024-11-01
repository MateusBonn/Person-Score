package com.mateus_bonn.pessoa_score.repository;

import com.mateus_bonn.pessoa_score.service.person.PersonFilter;
import com.mateus_bonn.pessoa_score.utils.PageFilter;

import java.util.List;

public interface PersonCustomRepository {
    List<PersonFilter> findAll(PageFilter pageFilter, List<String> searchBy);

    long count(PageFilter pageFilter, List<String> searchBy);
}
