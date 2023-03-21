package com.search.blog.code;

public class Constants {
    // Kakao
    public static final String HOST = "https://dapi.kakao.com";
    public static final String BLOG_API = "/v2/search/blog";
    public static final String REST_API_KEY = "KakaoAK 2882d90f4f6f45cf922eb85d2abba7ea";
    public static final Long KAKAO_TIME_OUT_MILLIS = 5000L; // 5000L = 5초

    // Naver
    public static final String CLIENT_ID = "4IG2RATzvBugggqJsfHi";
    public static final String CLIENT_SECRET = "Elu_V_BjjG";
    public static final String OPEN_API_NAVER = "https://openapi.naver.com/v1/search/blog?query=";
    public static final int NAVER_TIME_OUT_MILLIS = 5000; // 5000 = 5초

    // Trend
    public static final String RANK_LIMIT = "10";
}
