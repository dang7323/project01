package com.search.blog.service.logic;

import com.search.blog.service.logic.so.in.BlogInso;
import com.search.blog.service.logic.so.out.BlogOutso;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface NaverBlogService {

    List<BlogOutso> getSearchNaverBlog(BlogInso blogInso);

    List<BlogOutso> toBlogOutsos(String result);
}
