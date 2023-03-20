package com.search.blog.service.logic;

import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchBlogService {
    List<SearchBlogOutso> getSearchKakaoBlog(SearchBlogInso searchBlogInso);

    List<SearchBlogOutso> getSearchNaverBlog(SearchBlogInso searchBlogInso);

    void saveKeyword(SearchBlogInso searchBlogInso);

    List<SearchBlogOutso> fromKakaoJSONtoSearchBlogOutsos(String result);

    List<SearchBlogOutso> fromNaverJSONtoSearchBlogOutsos(String result);

}
