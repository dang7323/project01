package com.search.blog.presentation;


import com.search.blog.presentation.vo.req.KakaoRequest;
import com.search.blog.presentation.vo.req.NaverRequest;
import com.search.blog.presentation.vo.res.SearchBlogResponse;
import com.search.blog.service.logic.KakaoService;
import com.search.blog.service.logic.NaverService;
import com.search.blog.service.logic.TrendService;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class NaverController {

    @Autowired
    KakaoService kakaoService;
    @Autowired
    NaverService naverService;
    @Autowired
    TrendService trendService;

    @GetMapping("/naver")
    public List<SearchBlogResponse> searchblog(@Valid NaverRequest naverRequest) {
        List<SearchBlogResponse> searchBlogRespons;
        try {// Naver Api 호출
            searchBlogRespons = toSearchBlogResponses(naverService.getSearchNaverBlog(toSearchBlogInso(naverRequest)));
        } catch (ResourceAccessException e){ // Timeout 발생시 Naver api 호출
            log.info("Naver Search TimeOut!! Transfer to NaverBlog Search!!");
            // naverReq -> kakaoReq 변형
            searchBlogRespons = toSearchBlogResponses(kakaoService.getSearchKakaoBlog(toSearchBlogInso(naverRequest)));
        } finally {
            // DB에 keyword 저장
            trendService.saveKeyword(toSearchBlogInso(naverRequest));
        }

        return searchBlogRespons;
    }

     private SearchBlogInso toSearchBlogInso(NaverRequest naverRequest) {
        SearchBlogInso searchBlogInso = SearchBlogInso.builder()
                .keyword(naverRequest.getKeyword())
                .page(naverRequest.getStart())
                .size(naverRequest.getDisplay())
                .sort(naverRequest.getSort())
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