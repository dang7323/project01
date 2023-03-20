package com.search.blog.service.logic.so.out;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class SearchBlogOutso {
    private String title;
    private String contents;
    private String url;
    private String blogname;
    private String thumbnail;
    private LocalDateTime datetime;
}
