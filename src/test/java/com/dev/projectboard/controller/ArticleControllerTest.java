package com.dev.projectboard.controller;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


@DisplayName("View 컨트롤러 - 게시글")
@WebMvcTest(ArticleController.class)
class ArticleControllerTest {

    private final MockMvc mvc;


    ArticleControllerTest(@Autowired MockMvc mvc) { //테스트 패키지게 있는얘들은 @Autowired 생략 불가함
        this.mvc = mvc;
    }

    @Disabled("구현중")
    @DisplayName("[view] [GET} 게시글 리스트 (게시판) 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticlesView_thenReturnsArticlesView() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/articles")) //이런 요청을 했을때
                .andExpect(MockMvcResultMatchers.status().isOk())  //200ok 이여야하고
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML)) // 컨텐트 타입은 이거 여야함 : 뷰이므로 text_html 을 받음
                .andExpect(MockMvcResultMatchers.view().name("articles/index")) //이러한 view 가 있어야함
                .andExpect(MockMvcResultMatchers.model().attributeExists("articles"));//model 에 해당 키가 있는지 검증(서버로부터 데이터왔는지)
        //게시글 리스트 이므로 articles
    }

    @Disabled("구현중")
    @DisplayName("[view] [GET] 게시글 상세 페이지 - 정상 호출")
    @Test
    void givenNothing_whenRequestingArticleView_thenReturnsArticleView() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/article/1"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.view().name("articles/detail"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("article"))
                .andExpect(MockMvcResultMatchers.model().attributeExists("articleComments"));//게시글 이므로 댓글도 있어야함
    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출") //검색화면 불러오기
    @Test
    void givenNothing_whenRequestingArticleSearchView_thenReturnsArticlesSearchView() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/articles/search"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.model().attributeExists("articles/search"));
    }

    @Disabled("구현중")
    @DisplayName("[view][GET] 게시글 검색 전용 페이지 - 정상호출") //검색화면 불러오기
    @Test
    void givenNothing_whenRequestingArticleHashtagSearchView_thenReturnsArticlesHashtagSearchView() throws Exception {

        mvc.perform(MockMvcRequestBuilders.get("/articles/search-hashtag"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.TEXT_HTML))
                .andExpect(MockMvcResultMatchers.model().attributeExists("articles/search-hashtag"));
    }


}