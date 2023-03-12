package com.dev.projectboard.repository;

import com.dev.projectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


//@Repository 없어도됨 : JPA 구현체인 SimpleJpaRepository 에 이미 붙어있음

@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long> {
}