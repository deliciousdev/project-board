package com.dev.projectboard.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;


@Getter
@ToString

@Table (indexes ={
       @Index(columnList = "content"),
       @Index(columnList = "createdAt"),
       @Index(columnList = "createdBy")
})
@Entity
public class ArticleComment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // table 에서는 Article 의 id를 fk로 갖음 : 클래스에서는 객체지향적으로 객체를 갖고있으면됨. (DB테이블상에서, fk가 아닌 단순 article 참조여도됨)
    // optional= false : Article은 필수로 있어야하는값임
    //cascade ={} :디폴트 : ArticleCommment를 지웠을때 , 관련이있는 Article 이 영향을 받지않음 ( 댓글이 지워지든 말든 본문입장에선 상관없음) : 이런 경우 그냥 cascade 옵션을 안적어도됨
    @Setter @ManyToOne(optional = false, cascade = {}) private Article article ;
    @Setter @Column(nullable = false , length = 500) private String content;

    //메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt;
    @CreatedBy @Column(nullable = false) private String createdBy;
    @LastModifiedDate @Column(nullable = false) private LocalDate modifiedAt;
    @LastModifiedBy @Column(nullable = false) private String modifiedBy;

    protected ArticleComment() {
    }

    private ArticleComment(Article article, String content) {
        this.article = article;
        this.content = content;
    }
    public static ArticleComment of(Article article, String content) {
        return new ArticleComment(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ArticleComment)) return false;
        ArticleComment that = (ArticleComment) o;
        return this.id!=null && this.id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
