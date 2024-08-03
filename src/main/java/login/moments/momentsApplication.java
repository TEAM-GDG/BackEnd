package login.moments;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class momentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(momentsApplication.class, args);
	}

}
