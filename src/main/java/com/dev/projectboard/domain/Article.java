package com.dev.projectboard.domain;

import java.time.LocalDateTime;

public class Article {

    private Long id; //bigint -> Long
    private String title; // varchar -> String
    private String content;
    private String hashtag;

    //메타데이터
    private LocalDateTime createdAt; // datetime -> LocalDateTime
    private String createdBy; //
    private LocalDateTime modifiedAt;
    private String modifiedBy;
}
