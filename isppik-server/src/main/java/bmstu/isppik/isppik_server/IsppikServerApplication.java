package bmstu.isppik.isppik_server;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
@EnableScheduling
public class IsppikServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsppikServerApplication.class, args);
	}


}


