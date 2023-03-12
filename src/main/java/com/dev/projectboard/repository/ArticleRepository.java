package com.dev.projectboard.repository;

import com.dev.projectboard.domain.Article;
import com.dev.projectboard.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//@Repository 없어도됨 : JPA 구현체인 SimpleJpaRepository 에 이미 붙어있음

@RepositoryRestResource
public interface ArticleRepository extends
        JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>,
        QuerydslBinderCustomizer<QArticle>
{

    //Article에 있는 모든 필드들에 대한 검색이 열려 있다.
    //하지만 우리가 원하는것은 선택적인 필드들에 대해서만 검색 기능을 열어둘것이다.
    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {
        bindings.excludeUnlistedProperties(true); // 모든필드들에 대해서 검색을 허용하는것을 off함
        bindings.including(root.title,root.content,root.hashtag, root.createdAt,root.createdBy); //검색을 열어두길 원하는 필드들을 선택

        //exactly matching 이였던것을 바꿔주는 설정
//        bindings.bind(root.title).first(StringExpression::likeIgnoreCase); //like '%{v}' 이렇게 쿼리 날라감 : 검색어에 % 넣는것을 스스로해야함
        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); //like '%{v}' 이렇게 쿼리 날라감 : 검색어에 % 넣는것을 스스로해야함
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase); // like '%s{v}%' 이렇게 쿼리 날라감
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); //날짜 정확히 매칭이므로 추후에 바꿔주자
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}