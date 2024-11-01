package com.users.common.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.context.ApplicationListener;

@Configuration
public class DotenvConfig implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {

    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent event) {
        // .env 파일이 위치한 경로를 명시적으로 설정합니다.
        Dotenv dotenv = Dotenv.configure()
                .directory("C:/final_project/BackEnd-User-feature-user")
                .ignoreIfMissing()
                .load();

        ConfigurableEnvironment environment = event.getEnvironment();

        // 환경 변수를 시스템 속성에 추가
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
            // 디버깅용으로 콘솔에 출력
            System.out.println(entry.getKey() + ": " + entry.getValue());
        });
    }
}
