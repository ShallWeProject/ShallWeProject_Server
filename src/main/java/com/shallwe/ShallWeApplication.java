package com.shallwe;

import com.shallwe.global.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = "classpath:application-dev.yml", factory = YamlPropertySourceFactory.class)
public class ShallWeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShallWeApplication.class, args);
	}

}
