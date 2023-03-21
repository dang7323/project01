package com.search.blog.service.logic.so.in;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class BlogInso {
    String keyword;
    String sort;
    Integer page;
    Integer size;
}
