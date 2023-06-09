package com.search.blog.presentation;


import com.search.blog.presentation.vo.req.NaverBlogRequest;
import com.search.blog.presentation.vo.res.BlogResponse;
import com.search.blog.service.logic.KakaoBlogService;
import com.search.blog.service.logic.NaverBlogService;
import com.search.blog.service.logic.TrendService;
import com.search.blog.service.logic.so.in.BlogInso;
import com.search.blog.service.logic.so.out.BlogOutso;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class NaverBlogController {

    @Autowired
    KakaoBlogService kakaoBlogService;
    @Autowired
    NaverBlogService naverBlogService;
    @Autowired
    TrendService trendService;

    @GetMapping("/naver")
    public List<BlogResponse> searchblog(@Valid NaverBlogRequest naverBlogRequest) {
        List<BlogResponse> searchBlogRespons;
        try {// Naver Api 호출
            searchBlogRespons = toSearchBlogResponses(naverBlogService.getSearchNaverBlog(toSearchBlogInso(naverBlogRequest)));
        } catch (RuntimeException e){
            log.info("Naver generates exception. Transfer to Kakao!!");
            searchBlogRespons = toSearchBlogResponses(kakaoBlogService.getSearchKakaoBlog(toSearchBlogInso(naverBlogRequest)));
        } finally {
            // DB에 keyword 저장
            trendService.saveKeyword(toSearchBlogInso(naverBlogRequest));
        }

        return searchBlogRespons;
    }

     private BlogInso toSearchBlogInso(NaverBlogRequest naverBlogRequest) {
        BlogInso blogInso = BlogInso.builder()
                .keyword(naverBlogRequest.getKeyword())
                .page(naverBlogRequest.getStart())
                .size(naverBlogRequest.getDisplay())
                .sort(naverBlogRequest.getSort())
                .build();
        return blogInso;
    }

    private List<BlogResponse> toSearchBlogResponses(List<BlogOutso> blogOutsos) {
        List<BlogResponse> blogRespons = new ArrayList<>();
        for(BlogOutso blogOutso : blogOutsos){
            BlogResponse blogResponse = BlogResponse.builder()
                    .title(blogOutso.getTitle())
                    .contents(blogOutso.getContents())
                    .url(blogOutso.getUrl())
                    .blogname(blogOutso.getBlogname())
                    .thumbnail(blogOutso.getThumbnail())
                    .datetime(blogOutso.getDatetime())
                    .build();
            blogRespons.add(blogResponse);
        }
        return blogRespons;
    }
}