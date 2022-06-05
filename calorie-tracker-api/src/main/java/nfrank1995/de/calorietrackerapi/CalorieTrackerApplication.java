package nfrank1995.de.calorietrackerapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
public class CalorieTrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CalorieTrackerApplication.class, args);
	}

}
