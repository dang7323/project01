package com.search.blog.service.logic;

import com.search.blog.persistence.Entity.SearchEntity;
import com.search.blog.persistence.repository.SearchRepository;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.search.blog.code.Constants;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import com.google.gson.JsonObject;

import java.net.URI;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class SearchBlogServiceImpl implements SearchBlogService {

    @Autowired
    private SearchRepository searchRepository;

    @Override
    public List<SearchBlogOutso> getSearchBlog(SearchBlogInso searchBlogInso) {

        URI uri = UriComponentsBuilder.fromHttpUrl(Constants.HOST)
                .path(Constants.BLOG_API)
                .queryParam("query", searchBlogInso.getKeyword())
                .queryParam("page", searchBlogInso.getPage())
                .queryParam("size", searchBlogInso.getSize())
                .queryParam("sort", searchBlogInso.getSort())
                .encode()
                .build()
                .toUri();
        log.info("uri : {}", uri);
        RestTemplate restTemplate = new RestTemplate();

        RequestEntity<Void> req = RequestEntity
                .get(uri)
                .header("Authorization", Constants.REST_API_KEY)
                .build();

        ResponseEntity<String> result = restTemplate.exchange(req, String.class);
        log.info("result : {}", result.toString());
        List<SearchBlogOutso> searchBlogServiceOuts = fromJSONtoSearchBlogOutsos(result.getBody());

        return searchBlogServiceOuts;
    }

    @Override
    public List<SearchBlogOutso> fromJSONtoSearchBlogOutsos(String result) {
        JsonObject jsonObj = (JsonObject) new JsonParser().parse(result);
        JsonArray jsonArr = (JsonArray) jsonObj.get("documents");
        List<SearchBlogOutso> searchBlogServiceOuts = new ArrayList<>();

        for(Object arr : jsonArr){
            JsonObject obj = (JsonObject) arr;
            SearchBlogOutso searchBlogServiceOut = new SearchBlogOutso();
            searchBlogServiceOut.setTitle(obj.get("title").toString());
            searchBlogServiceOut.setContents(obj.get("contents").toString());
            searchBlogServiceOut.setUrl(obj.get("url").toString());
            searchBlogServiceOut.setBlogname(obj.get("blogname").toString());
            searchBlogServiceOut.setThumbnail(obj.get("thumbnail").toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
            LocalDateTime dateTime = LocalDateTime.parse(obj.get("datetime").toString().substring(1,20), formatter);
            searchBlogServiceOut.setDatetime(dateTime);
            searchBlogServiceOuts.add(searchBlogServiceOut);
        }
        return searchBlogServiceOuts;
    }

    @Override
    public void saveKeyword(SearchBlogInso searchBlogInso) {
        SearchEntity search = new SearchEntity();
        search.setKeyword(searchBlogInso.getKeyword());
        searchRepository.save(search);
    }
}
