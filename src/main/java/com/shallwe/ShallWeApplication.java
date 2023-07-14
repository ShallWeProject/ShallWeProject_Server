package com.shallwe;

import com.shallwe.global.config.YamlPropertySourceFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@PropertySource(value = { "classpath:oauth2/application-oauth2.yml" }, factory = YamlPropertySourceFactory.class)
@PropertySource(value = {"classpath:application.yml"}, factory = YamlPropertySourceFactory.class)
public class ShallWeApplication {

	public static void main(String[] args) {
		SpringApplication.run(ShallWeApplication.class, args);
	}

}
