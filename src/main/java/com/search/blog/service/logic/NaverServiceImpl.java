package com.search.blog.service.logic;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.search.blog.service.logic.so.in.SearchBlogInso;
import com.search.blog.service.logic.so.out.SearchBlogOutso;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.search.blog.code.Constants.*;

@Slf4j
@Service
public class NaverServiceImpl implements NaverService {

    // 네이버 검색 API
    @Override
    public List<SearchBlogOutso> getSearchNaverBlog(SearchBlogInso searchBlogInso) {
        String clientId = CLIENT_ID; //애플리케이션 클라이언트 아이디
        String clientSecret = CLIENT_SECRET; //애플리케이션 클라이언트 시크릿

        String text = null;
        try {
            text = URLEncoder.encode(searchBlogInso.getKeyword(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("검색어 인코딩 실패", e);
        }

        String apiURL = OPEN_API_NAVER + text;// JSON 결과
        if(searchBlogInso.getSize() != null){
            apiURL += "&display=" + searchBlogInso.getSize();
        }
        if(searchBlogInso.getPage() != null){
            apiURL += "&start=" + searchBlogInso.getPage();
        }
        if(searchBlogInso.getSort() != null){
            apiURL += "&sort=" + searchBlogInso.getSort();
        }

        log.info("Naver Api uri : {}", apiURL);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);
        String responseBody = get(apiURL, requestHeaders);


        log.info("Naver Api result : {}", responseBody);

        List<SearchBlogOutso> searchBlogServiceOuts = fromNaverJSONtoSearchBlogOutsos(responseBody);

        return searchBlogServiceOuts;
    }

    private static String get(String apiUrl, Map<String, String> requestHeaders) {
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for (Map.Entry<String, String> header : requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }


            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl) {
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection) url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body) {
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }


    @Override
    public List<SearchBlogOutso> fromNaverJSONtoSearchBlogOutsos(String result) {
        JsonObject jsonObj = (JsonObject) new JsonParser().parse(result);
        JsonArray jsonArr = (JsonArray) jsonObj.get("items");
        List<SearchBlogOutso> searchBlogServiceOuts = new ArrayList<>();

        for (Object arr : jsonArr) {
            JsonObject obj = (JsonObject) arr;
            SearchBlogOutso searchBlogServiceOut = new SearchBlogOutso();
            searchBlogServiceOut.setTitle(obj.get("title").toString());
            searchBlogServiceOut.setContents(obj.get("description").toString());
            searchBlogServiceOut.setUrl(obj.get("link").toString());
            searchBlogServiceOut.setBlogname(obj.get("bloggername").toString());
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
            LocalDateTime dateTime = LocalDate.parse(obj.get("postdate").toString().substring(1, 9), formatter).atStartOfDay();
            ;
            searchBlogServiceOut.setDatetime(dateTime);
            searchBlogServiceOuts.add(searchBlogServiceOut);
        }
        return searchBlogServiceOuts;
    }
}
