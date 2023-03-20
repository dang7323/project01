package com.search.blog.presentation;


import com.search.blog.presentation.vo.res.SearchBlogResponse;
import com.search.blog.presentation.vo.req.SearchBlogRequest;
import com.search.blog.service.logic.SearchBlogService;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class SearchBlogController {

    @Autowired
    SearchBlogService searchBlogService;

    @GetMapping("/searchblog")
    public List<SearchBlogResponse> searchblog(@Valid SearchBlogRequest searchBlogRequest) {
        // Kakao Api 호출
        List<SearchBlogResponse> searchBlogRespons = toSearchBlogResponses(searchBlogService.getSearchBlog(toSearchBlogInso(searchBlogRequest)));

        // DB에 keyword 저장
        searchBlogService.saveKeyword(toSearchBlogInso(searchBlogRequest));

        return searchBlogRespons;
    }

    private SearchBlogInso toSearchBlogInso(SearchBlogRequest searchBlogRequest) {
        SearchBlogInso searchBlogInso = SearchBlogInso.builder()
                .keyword(searchBlogRequest.getKeyword())
                .page(searchBlogRequest.getPage())
                .size(searchBlogRequest.getSize())
                .sort(searchBlogRequest.getSort())
                .build();
        return searchBlogInso;
    }

    private List<SearchBlogResponse> toSearchBlogResponses(List<SearchBlogOutso> searchBlogOutsos) {
        List<SearchBlogResponse> searchBlogResponses = new ArrayList<>();
        for(SearchBlogOutso searchBlogOutso : searchBlogOutsos){
            SearchBlogResponse searchBlogResponse = SearchBlogResponse.builder()
                    .title(searchBlogOutso.getTitle())
                    .contents(searchBlogOutso.getContents())
                    .url(searchBlogOutso.getUrl())
                    .blogname(searchBlogOutso.getBlogname())
                    .thumbnail(searchBlogOutso.getThumbnail())
                    .datetime(searchBlogOutso.getDatetime())
                    .build();
            searchBlogResponses.add(searchBlogResponse);
        }
        return searchBlogResponses;
    }
}