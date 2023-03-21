package com.search.blog.presentation;


import com.search.blog.persistence.repository.TrendCriteria;
import com.search.blog.presentation.vo.res.TrendResponse;
import com.search.blog.service.logic.TrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class TrendController {

    @Autowired
    TrendService trendService;

    @GetMapping("/trend")
    public List<TrendResponse> searchtrend() {
        List<TrendResponse> searchTrendRespons = toSearchTrendResponses(trendService.getSearchTrendCnt());
        return searchTrendRespons;
    }

    private List<TrendResponse> toSearchTrendResponses(List<TrendCriteria> trendCriterias) {
        List<TrendResponse> searchTrendRespons = new ArrayList<>();

        for(TrendCriteria trendCriteria : trendCriterias){
            TrendResponse trendResponse = new TrendResponse();
            trendResponse.setKeyword(trendCriteria.getKeyword());
            trendResponse.setCount(trendCriteria.getCount());
            searchTrendRespons.add(trendResponse);
        }

        return searchTrendRespons;
    }
}