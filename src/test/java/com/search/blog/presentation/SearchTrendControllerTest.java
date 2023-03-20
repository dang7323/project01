package com.search.blog.presentation;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.nio.charset.Charset;
import java.util.Arrays;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class SearchTrendControllerTest {

    @Autowired
    SearchTrendController searchTrendController;

    @Autowired
    SearchBlogController searchBlogController;

    private MockMvc mockMvc;

    private MockMvc mockMvcSearchBlog;


    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchTrendController).build();
        mockMvcSearchBlog = MockMvcBuilders.standaloneSetup(searchBlogController).build();
    }

    @Test
    public void searchtrend() throws Exception {
        mockMvcSearchBlog.perform(get("/searchblog").param("keyword", "IU").param("size", "10"));
        mockMvcSearchBlog.perform(get("/searchblog").param("keyword", "IU").param("size", "10"));
        mockMvcSearchBlog.perform(get("/searchblog").param("keyword", "BTS").param("size", "10"));

        mockMvc.perform(get("/searchtrend").contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(2)))
                .andExpect(jsonPath("$.[0].count", equalTo(2)))
                .andExpect(jsonPath("$.[0].keyword", equalTo("IU")))
                .andExpect(jsonPath("$.[1].count", equalTo(1)))
                .andExpect(jsonPath("$.[1].keyword", equalTo("BTS")))
                .andDo(print());
    }
}