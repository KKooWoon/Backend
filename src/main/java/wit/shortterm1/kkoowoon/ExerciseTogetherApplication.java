package wit.shortterm1.kkoowoon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "wit.shortterm1.kkoowoon.domain")
//@EntityScan(basePackages = "wit.shortterm1.kkoowoon.domain")
@EnableJpaAuditing
	public class ExerciseTogetherApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExerciseTogetherApplication.class, args);
	}

}
