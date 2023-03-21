package com.search.blog.presentation.vo.req;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data
public class NaverRequest {
    @NotBlank(message = "keyword must not be blank.")
    String keyword;

    @Pattern(regexp = "|sim|date", message="sort must be sim or date or null")
    String sort;

    @Min(value=1) @Max(value=100)
    Integer start;

    @Min(value=1) @Max(value=100)
    Integer display;
}
