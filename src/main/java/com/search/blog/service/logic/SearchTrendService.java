package com.search.blog.service.logic;

import com.search.blog.persistence.repository.SearchTrendCriteria;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchTrendService {

    List<SearchTrendCriteria> getSearchTrendCnt();
}
