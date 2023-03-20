package com.search.blog.service.logic;

import com.search.blog.persistence.repository.SearchTrendCriteria;
import com.search.blog.persistence.repository.SearchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SearchTrendServiceImpl implements SearchTrendService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<SearchTrendCriteria> getSearchTrendCnt() {
        List<SearchTrendCriteria> searchTrendCriterias = searchRepository.countSearchTrend();
        return searchTrendCriterias;
    }
}
