package com.search.blog.service.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.search.blog.code.Constants;
import com.search.blog.service.logic.so.in.BlogInso;
import com.search.blog.service.logic.so.out.BlogOutso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.search.blog.code.Constants.KAKAO_TIME_OUT_MILLIS;

@Slf4j
@Service
public class KakaoBlogServiceImpl implements KakaoBlogService {

    // 카카오 검색 API
    @Override
    public List<BlogOutso> getSearchKakaoBlog(BlogInso blogInso) {

        URI uri = UriComponentsBuilder.fromHttpUrl(Constants.HOST)
                .path(Constants.BLOG_API)
                .queryParam("query", blogInso.getKeyword())
                .queryParam("page", blogInso.getPage())
                .queryParam("size", blogInso.getSize())
                .queryParam("sort", blogInso.getSort())
                .encode()
                .build()
                .toUri();
        log.info("Kakao Api uri : {}", uri);

        RestTemplate restTemplate = new RestTemplateBuilder()
                .setConnectTimeout(Duration.ofMillis(KAKAO_TIME_OUT_MILLIS))
                .setReadTimeout(Duration.ofMillis(KAKAO_TIME_OUT_MILLIS))
                .build();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("Authorization", Constants.REST_API_KEY)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        log.info("Kakao Api result : {}", result.toString());
        List<BlogOutso> searchBlogServiceOuts = fromKakaoJSONtoSearchBlogOutsos(result.getBody());

        return searchBlogServiceOuts;
    }

    @Override
    public List<BlogOutso> fromKakaoJSONtoSearchBlogOutsos(String result) {
        JsonObject jsonObj = (JsonObject) new JsonParser().parse(result);
        JsonArray jsonArr = (JsonArray) jsonObj.get("documents");
        List<BlogOutso> searchBlogServiceOuts = new ArrayList<>();

        for (Object arr : jsonArr) {
            JsonObject obj = (JsonObject) arr;
            BlogOutso searchBlogServiceOut = new BlogOutso();
            searchBlogServiceOut.setTitle(obj.get("title").toString());
            searchBlogServiceOut.setContents(obj.get("contents").toString());
            searchBlogServiceOut.setUrl(obj.get("url").toString());
            searchBlogServiceOut.setBlogname(obj.get("blogname").toString());
            searchBlogServiceOut.setThumbnail(obj.get("thumbnail").toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(obj.get("datetime").toString().substring(1, 20), formatter);
            searchBlogServiceOut.setDatetime(dateTime);
            searchBlogServiceOuts.add(searchBlogServiceOut);
        }
        return searchBlogServiceOuts;
    }
}
