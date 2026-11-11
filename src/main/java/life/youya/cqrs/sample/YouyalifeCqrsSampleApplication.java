package life.youya.cqrs.sample;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages={"life.youya.cqrs"})
public class YouyalifeCqrsSampleApplication {
	public static void main(String[] args) {
		SpringApplication.run(YouyalifeCqrsSampleApplication.class, args);
	}
}
