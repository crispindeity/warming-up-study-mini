package org.example.yeonghuns;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Stream;
@EnableJpaAuditing
@SpringBootApplication
public class YeongHunsApplication {

	public static void main(String[] args) {
		SpringApplication.run(YeongHunsApplication.class, args);}

}
