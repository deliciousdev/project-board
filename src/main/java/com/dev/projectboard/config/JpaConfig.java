package com.dev.projectboard.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Optional;


@EnableJpaAuditing
@Configuration
public class JpaConfig {

    @Bean
    public AuditorAware<String> auditorAware(){//사람 이름이니까 타입은 String
        // todo 임시로 해놨는데 나중에 바꿔줘야함 : 스프링 스큐리티로 인증 기능을 붙이게 될 때, 수정하자
        //Optional 반환값을 줘야함
        //jpa 가 auditing 할때마다 이 이름을 넣어줌
        return () -> Optional.of("eno");
    }
}
