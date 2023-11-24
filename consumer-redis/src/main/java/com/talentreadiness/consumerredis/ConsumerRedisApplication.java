package com.talentreadiness.consumerredis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.talentreadiness.database", "com.talentreadiness.consumerredis" })
public class ConsumerRedisApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumerRedisApplication.class, args);
	}

}
