package bmstu.isppik.isppik_server;

import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@AllArgsConstructor
public class IsppikServerApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(IsppikServerApplication.class, args);
	}

	private final PasswordEncoder encoder;

	@Override
	public void run(String... args) throws Exception {
		System.out.println(encoder.encode("admin"));
	}
}
