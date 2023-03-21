package com.search.blog.service.logic;

import com.search.blog.persistence.repository.SearchTrendCriteria;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrendService {

    List<SearchTrendCriteria> getSearchTrendCnt();

    void saveKeyword(SearchBlogInso searchBlogInso);
}
