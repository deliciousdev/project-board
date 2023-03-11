package com.dev.projectboard.repository;

import com.dev.projectboard.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;


//@Repository 없어도됨 : JPA 구현체인 SimpleJpaRepository 에 이미 붙어있음
public interface ArticleRepository extends JpaRepository<Article, Long> {
}