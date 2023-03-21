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

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@Rollback(false)
public class KakaoBlogControllerTest {

    @Autowired
    KakaoBlogController searchBlogController;

    private MockMvc mockMvc;

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(searchBlogController).build();
    }

    @Test
    public void testUserController_Return200() throws Exception {
        mockMvc.perform(get("/kakao").param("keyword", "IU").param("size", "10")
                .contentType(contentType))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", equalTo(10)))
                .andDo(print());
    }

    @Test
    public void testUserController_WithEmptyKeyword_Return400() throws Exception {
        mockMvc.perform(get("/kakao").param("keyword", "").param("size", "10")
                        .contentType(contentType))
                .andExpect(status().isBadRequest())
                .andDo(print());
    }
}