package com.bopp.dbluftlinierechner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.bopp.dbluftlinierechner.api.v1.distance.LuftlinierechnerUtility;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		LuftlinierechnerUtility.init();
		SpringApplication.run(Application.class, args);
	}
}
