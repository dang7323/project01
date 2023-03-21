package com.search.blog.service.logic;

import com.search.blog.persistence.repository.TrendCriteria;
import com.search.blog.service.logic.so.in.BlogInso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TrendService {

    List<TrendCriteria> getSearchTrendCnt();

    void saveKeyword(BlogInso blogInso);
}
