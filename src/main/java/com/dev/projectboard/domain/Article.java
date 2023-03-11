package com.dev.projectboard.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;


@Getter
@ToString
//@EqualsAndHashCode

@Table(indexes = { //검색을 할 수 있는 항목들
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
@EntityListeners(AuditingEntityListener.class)//엔티티에는 Auditing이 쓰인다는 표시를 해줘야함
@Entity
public class Article {

    @Id //프라이머리 키
    @GeneratedValue(strategy = GenerationType.IDENTITY)
//키값 자동증가 , AUTO 가 기본값인데 IDENTITY로 바꿔줘야함 MySQL 에서 키값자동증가는 IDENTITY 옵션으로 해야함
    private Long id;


    @Setter
    @Column(nullable = false)
    private String title;
    @Setter
    @Column(nullable = false, length = 10000)
    private String content;

    @Setter
    private String hashtag; //선택적 이라서 null 가능


    //양방향 바인딩을 완성 : List, Set Map 등 용도에맞게 원하는걸로 하면됨 :
    //Set 의 의도 : 중복을 허용하지 않겠다.
    //cascade 가 걸려있으면 연관 되어있기 때문에, 얘 를 삭제하면 딸린 식구들까지 다 삭제됨(FK를 걸어주는 개념임 : 부모없는 자식을 만들지 않겠다는 의미: 부모가 삭제되면 자식도 자동으로 삭제 시키겠다는 의미)
    //cascade 걸어줄지 말지는 팀에서 상의해서 정하는거임
    @OrderBy("id")//정렬기준은 id로 할거임
    @OneToMany(mappedBy = "article", cascade= CascadeType.ALL)
    @ToString.Exclude //article 이라고 이름을 정해주지않으면 두 엔티티 이름을 합쳐서 테이블을 따로 만듬
    private final Set<ArticleComment> articleComments = new LinkedHashSet<>();



    //메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;

    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; //

    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt;

    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy;



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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Article)) return false;
        Article article = (Article) o;
        return id != null && id.equals(article.id); //id!= null 부분 의 의미 : 영속화 되지 않은 엔티티는 동등비교 자격조차 없음(내용이 전부 같더라도 다른취급하겠다
//        return id.equals(article.id);
    }
    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
