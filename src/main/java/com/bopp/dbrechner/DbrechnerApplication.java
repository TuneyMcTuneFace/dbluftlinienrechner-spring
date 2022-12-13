package com.bopp.dbrechner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bopp.dbrechner.api.v1.distance.LuftwegrechnerUtility;

@SpringBootApplication
public class DbrechnerApplication {
	public static void main(String[] args) {
		LuftwegrechnerUtility.init();
		SpringApplication.run(DbrechnerApplication.class, args);
	}
}
