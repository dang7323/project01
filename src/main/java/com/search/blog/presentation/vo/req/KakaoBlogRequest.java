package com.search.blog.presentation.vo.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class KakaoBlogRequest {
    @NotBlank(message = "keyword must not be blank.")
    String keyword;

    @Pattern(regexp = "|accuracy|recency", message="sort must be accuracy or recency or null")
    String sort;

    @Min(value=1) @Max(value=50)
    Integer page;

    @Min(value=1) @Max(value=50)
    Integer size;
}
