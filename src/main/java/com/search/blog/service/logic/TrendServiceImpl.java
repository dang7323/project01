package com.search.blog.service.logic;

import com.search.blog.persistence.Entity.SearchEntity;
import com.search.blog.persistence.repository.SearchTrendCriteria;
import com.search.blog.persistence.repository.SearchRepository;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<SearchTrendCriteria> getSearchTrendCnt() {
        List<SearchTrendCriteria> searchTrendCriterias = searchRepository.countSearchTrend();
        return searchTrendCriterias;
    }

    @Override
    public void saveKeyword(SearchBlogInso searchBlogInso) {
        SearchEntity search = new SearchEntity();
        search.setKeyword(searchBlogInso.getKeyword());
        searchRepository.save(search);
    }
}
