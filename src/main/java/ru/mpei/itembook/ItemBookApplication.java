package ru.mpei.itembook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

@EntityScan(basePackageClasses = {ItemBookApplication.class, Jsr310JpaConverters.class})
@SpringBootApplication
public class ItemBookApplication {
	public static void main(String[] args) {
		SpringApplication.run(ItemBookApplication.class, args);
	}
}
