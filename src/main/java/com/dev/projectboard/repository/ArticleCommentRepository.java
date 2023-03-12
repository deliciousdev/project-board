package com.dev.projectboard.repository;

import com.dev.projectboard.domain.ArticleComment;
import com.dev.projectboard.domain.QArticleComment;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface ArticleCommentRepository extends
        JpaRepository<ArticleComment, Long>,
        QuerydslPredicateExecutor<ArticleComment>,//기본검색기능
        QuerydslBinderCustomizer<QArticleComment> //기능추가
{

    @Override //사용자 정의 기능을 넣어줄려면, 인터페이스이므로 default 메서드여야함
    default void customize(QuerydslBindings bindings, QArticleComment root) {

        bindings.excludeUnlistedProperties(true);
        bindings.including(root.content, root.createdAt,root.createdBy);
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq);
        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);

    }
}