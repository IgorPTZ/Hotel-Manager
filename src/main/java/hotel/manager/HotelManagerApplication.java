package hotel.manager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EntityScan(basePackages = {"hotel.manager.model"})
@ComponentScan(basePackages = {"hotel.*"})
@EnableJpaRepositories(basePackages = {"hotel.manager.repository"})
@EnableTransactionManagement
@EnableScheduling
public class HotelManagerApplication {

	public static void main(String[] args) {
		SpringApplication.run(HotelManagerApplication.class, args);
	}
}
