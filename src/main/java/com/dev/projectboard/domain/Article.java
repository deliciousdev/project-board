package com.dev.projectboard.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;


@Getter
@ToString
//@EqualsAndHashCode

@Table(indexes = { //검색을 할 수 있는 항목들
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@Entity
public class Article {

    @Id //프라이머리 키
    @GeneratedValue(strategy = GenerationType.IDENTITY)//키값 자동증가 , AUTO 가 기본값인데 IDENTITY로 바꿔줘야함 MySQL 에서 키값자동증가는 IDENTITY 옵션으로 해야함
    private Long id;


    @Setter @Column(nullable = false) private String title;
    @Setter @Column(nullable = false, length = 10000) private String content;

    @Setter private String hashtag; //선택적 이라서 null 가능

   //메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; //
    @LastModifiedDate private LocalDateTime modifiedAt;
    @LastModifiedBy @Column(nullable = false,length = 100) private String modifiedBy;


    protected Article() {
    }

    private Article(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }

    public static Article of(String title, String content, String hashtag) {
        return new Article(title, content, hashtag);
    }



}
