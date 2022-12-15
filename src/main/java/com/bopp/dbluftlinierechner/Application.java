package com.bopp.dbluftlinierechner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.bopp.dbluftlinierechner.api.v1.distance.LuftlinierechnerUtility;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		LuftlinierechnerUtility.init("/data/D_Bahnhof_2020_alle.CSV"); // Liest die CSV aus dem resources Ordner heraus
		SpringApplication.run(Application.class, args);
	}
}
