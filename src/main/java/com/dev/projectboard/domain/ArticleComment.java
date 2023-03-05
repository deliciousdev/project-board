package com.dev.projectboard.domain;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class ArticleComment {

    private Long id;

    // table 에서는 Article 의 id를 fk로 갖음 : 클래스에서는 객체지향적으로 객체를 갖고있으면됨. (물론 fk가 아니고 단순히 article 참조여도됨)
    private Article article ;
    private String content;

    //메타데이터
    private LocalDateTime createdAt;
    private String createdBy;
    private LocalDate modifiedAt;
    private String modifiedBy;


}
