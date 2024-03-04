package org.example.yeonghuns.config.audit;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing
public class AuditConfig {
    // 추후 생성한 사람, 수정한 사람 기능 구현시 추가로 구현
    //[김기태의 JAVA를 자바](https://www.youtube.com/watch?v=D0c4t2NSYF4)
    //23년 스프링 (48) Auditing 설정 클래스 생성하기
}
