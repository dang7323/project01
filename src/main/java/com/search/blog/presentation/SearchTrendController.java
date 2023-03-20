package com.search.blog.presentation;


import com.search.blog.persistence.repository.SearchTrendCriteria;
import com.search.blog.presentation.vo.res.SearchTrendResponse;
import com.search.blog.service.logic.SearchTrendService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchTrendController {

    @Autowired
    SearchTrendService searchTrendService;

    @GetMapping("/searchtrend")
    public List<SearchTrendResponse> searchtrend() {
        List<SearchTrendResponse> searchTrendRespons = toSearchTrendResponses(searchTrendService.getSearchTrendCnt());
        return searchTrendRespons;
    }

    private List<SearchTrendResponse> toSearchTrendResponses(List<SearchTrendCriteria> searchTrendCriterias) {
        List<SearchTrendResponse> searchTrendRespons = new ArrayList<>();

        for(SearchTrendCriteria searchTrendCriteria : searchTrendCriterias){
            SearchTrendResponse searchTrendResponse = new SearchTrendResponse();
            searchTrendResponse.setKeyword(searchTrendCriteria.getKeyword());
            searchTrendResponse.setCount(searchTrendCriteria.getCount());
            searchTrendRespons.add(searchTrendResponse);
        }

        return searchTrendRespons;
    }
}