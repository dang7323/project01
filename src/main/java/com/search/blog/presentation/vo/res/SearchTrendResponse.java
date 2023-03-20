package com.search.blog.presentation.vo.res;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchTrendResponse {
    private String keyword;
    private Integer count;
}
