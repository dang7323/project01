package com.search.blog.presentation;


import com.search.blog.presentation.vo.req.KakaoRequest;
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
public class KakaoController {

    @Autowired
    KakaoService kakaoService;

    @Autowired
    NaverService naverService;

    @Autowired
    TrendService trendService;

    @GetMapping("/kakao")
    public List<SearchBlogResponse> searchblog(@Valid KakaoRequest kakaoRequest) {
        List<SearchBlogResponse> searchBlogRespons;
        try {// Kakao Api 호출
            searchBlogRespons = toSearchBlogResponses(kakaoService.getSearchKakaoBlog(toSearchBlogInso(kakaoRequest)));
        } catch (ResourceAccessException e){ // Timeout 발생시 Naver api 호출
            log.info("KaKaoBlog Search TimeOut!! Transfer to NaverBlog Search!!");
            searchBlogRespons = toSearchBlogResponses(naverService.getSearchNaverBlog(toSearchBlogInso(kakaoRequest)));
        } finally {
            // DB에 keyword 저장
            trendService.saveKeyword(toSearchBlogInso(kakaoRequest));
        }

        return searchBlogRespons;
    }

     private SearchBlogInso toSearchBlogInso(KakaoRequest kakaoRequest) {
        SearchBlogInso searchBlogInso = SearchBlogInso.builder()
                .keyword(kakaoRequest.getKeyword())
                .page(kakaoRequest.getPage())
                .size(kakaoRequest.getSize())
                .sort(kakaoRequest.getSort())
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