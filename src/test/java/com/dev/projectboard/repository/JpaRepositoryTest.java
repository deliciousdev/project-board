package com.dev.projectboard.repository;


import com.dev.projectboard.config.JpaConfig;
import com.dev.projectboard.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("testdb")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY) //ANY가 디폴트임 : db설정을 무시하고 기본인 h2인메모리를 사용하여 테스트하게됨.
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) //테스트에서 테스트용 DB인 h2인메모리를 사용하지않고, 따로 설정해놓은것을 불러오게됨(설정해준 운영 DB or 설정해준 테스트 DB)
//이 어노테이션을 .yaml 에 넣어서 모든 테스트는 이 어노테이션이 붙은것럼 해보자(어차피 테스트 1개 니까 그냥 그렇게 하자)

@Import(JpaConfig.class)//JpaConfig는 내가 만든 클래스이므로 @DataJpaTest 는 이것을 몰름.
// JpaConfig 에 있는 설정을 @DataJpaTest 에 알려주기위해서 임포트해야함
//임포트를 안해주면 JpaConfig에 넣어준 옵션인 @EnableJpaAuditing 기능이 안켜짐
@DataJpaTest
class JpaRepositoryTest {

    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;

    //여기 Autowired 안넣어주면 자동으로 Autowired 안되네
    JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }

    @DisplayName("select 테스트")
    @Test//테스트데이터가 DB에 있을때 select 을 하면 잘 동작한다
    void givenTestData_whenSelecting_thenWorksFine(){
        //Given

        //When
        List<Article> articles = articleRepository.findAll();

        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(123);
    }

    @DisplayName("insert 테스트")
    @Test//기존에 있던 데이터에서 insert를 하면 데이터 숫자가 하나 더 늘어있다
    void givenTestData_whenInserting_thenWorksFine(){

        //Given
        long previousCount = articleRepository.count();

        //when
        Article article = Article.of("mytitle", "mycontent", "#myhashtag");
        articleRepository.save(article);

        //then
        assertThat(articleRepository.count()).isEqualTo(previousCount + 1);
    }


    @DisplayName("update 테스트")
    @Test//엔티티를 하나 꺼내고 수정한한다. 그리고저 저장한후 업데이트가 잘 되었는지 확인하자
    void givenTestData_WhenUpdating_thenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String hashtagToBeUpdated = "#hashhash";
        article.setHashtag(hashtagToBeUpdated);

        //When
        Article resultOfUpdate = articleRepository.save(article);
        articleRepository.flush();
//        Article resultOfUpdate = articleRepository.saveAndFlush(article);

        //Then
        assertThat(resultOfUpdate).hasFieldOrPropertyWithValue("hashtag", hashtagToBeUpdated);
    }

    @DisplayName("delete 테스트")
    @Test//엔티티를 하나꺼내온후 그 해당되는 id를 삭제해보자. 삭제를 하면 데이터가 하나 줄어들것이다.또한 연관관계 있는것도 삭제가 되어야함
    void givenTestData_WhenDeleting_thenWorksFine(){

        //Given
        Article article = articleRepository.findById(2L).orElseThrow();
        long previousArticleCount = articleRepository.count();
        long previousArticleCommentCount = articleCommentRepository.count();
        int toBeDeletedCommentCount = article.getArticleComments().size();

        //When
        articleRepository.delete(article);

        //Then
        assertThat(articleRepository.count()).isEqualTo(previousArticleCount - 1);
        assertThat(articleCommentRepository.count()).isEqualTo(previousArticleCommentCount - toBeDeletedCommentCount);

    }
}
