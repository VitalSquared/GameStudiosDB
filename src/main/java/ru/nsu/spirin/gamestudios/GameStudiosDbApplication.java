package ru.nsu.spirin.gamestudios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.*;
import java.util.Properties;

@SpringBootApplication
public class GameStudiosDbApplication {

	public static void main(String[] args) throws IOException {
		InputStream inputStream = ClassLoader.getSystemResourceAsStream("application.properties");
		if (inputStream == null) {

			return;
		}

		SpringApplication application = new SpringApplication(GameStudiosDbApplication.class);

		Properties properties = new Properties();
		properties.load(inputStream);

		File f = new File("firstTimeFileChecker");
		if (!f.createNewFile()) {
			properties.put("spring.datasource.initialization-mode", "never");
		}
		else {
			properties.put("spring.datasource.initialization-mode", "always");
		}
		application.setDefaultProperties(properties);
		application.run(args);
	}

}
