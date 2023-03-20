package com.search.blog.service.logic;

import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SearchBlogService {
    List<SearchBlogOutso> getSearchBlog(SearchBlogInso searchBlogInso);

    void saveKeyword(SearchBlogInso searchBlogInso);

    List<SearchBlogOutso> fromJSONtoSearchBlogOutsos(String result);

}
