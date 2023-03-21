package com.search.blog.service.logic;

import com.search.blog.persistence.Entity.TrendEntity;
import com.search.blog.persistence.repository.TrendRepository;
import com.search.blog.persistence.repository.TrendCriteria;
import com.search.blog.service.logic.so.in.BlogInso;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrendServiceImpl implements TrendService {

    @Autowired
    private TrendRepository trendRepository;

    @Override
    public List<TrendCriteria> getSearchTrendCnt() {
        List<TrendCriteria> trendCriteria = trendRepository.countSearchTrend();
        return trendCriteria;
    }

    @Override
    public void saveKeyword(BlogInso blogInso) {
        TrendEntity search = new TrendEntity();
        search.setKeyword(blogInso.getKeyword());
        trendRepository.save(search);
    }
}
