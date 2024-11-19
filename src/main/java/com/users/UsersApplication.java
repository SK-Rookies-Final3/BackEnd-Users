package com.users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class UsersApplication {
	public static void main(String[] args) {
		System.out.println("DB_URL: " + System.getProperty("DB_URL")); // 로그로 확인
		SpringApplication.run(UsersApplication.class, args);
	}
}