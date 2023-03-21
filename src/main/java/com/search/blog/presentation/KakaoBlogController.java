package com.search.blog.presentation;


import com.search.blog.presentation.vo.req.KakaoBlogRequest;
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
import org.springframework.web.client.ResourceAccessException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
public class KakaoBlogController {

    @Autowired
    KakaoBlogService kakaoBlogService;

    @Autowired
    NaverBlogService naverBlogService;

    @Autowired
    TrendService trendService;

    @GetMapping("/kakao")
    public List<BlogResponse> searchblog(@Valid KakaoBlogRequest kakaoBlogRequest) {
        List<BlogResponse> searchBlogRespons;
        try {// Kakao Api 호출
            searchBlogRespons = toSearchBlogResponses(kakaoBlogService.getSearchKakaoBlog(toSearchBlogInso(kakaoBlogRequest)));
        } catch (ResourceAccessException e){ // Timeout 발생시 Naver api 호출
            log.info("KaKaoBlog Search TimeOut!! Transfer to NaverBlog Search!!");
            searchBlogRespons = toSearchBlogResponses(naverBlogService.getSearchNaverBlog(toSearchBlogInso(kakaoBlogRequest)));
        } finally {
            // DB에 keyword 저장
            trendService.saveKeyword(toSearchBlogInso(kakaoBlogRequest));
        }

        return searchBlogRespons;
    }

     private BlogInso toSearchBlogInso(KakaoBlogRequest kakaoBlogRequest) {
        BlogInso blogInso = BlogInso.builder()
                .keyword(kakaoBlogRequest.getKeyword())
                .page(kakaoBlogRequest.getPage())
                .size(kakaoBlogRequest.getSize())
                .sort(kakaoBlogRequest.getSort())
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