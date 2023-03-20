package com.search.blog.service.logic.so.in;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class KakaoRequestVariableDto {
    String query;
    Integer page;
    Integer size;
    String sort;
}