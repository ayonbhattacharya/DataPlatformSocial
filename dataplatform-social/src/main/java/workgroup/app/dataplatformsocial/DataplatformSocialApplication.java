package workgroup.app.dataplatformsocial;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import workgroup.app.dataplatformsocial.application.impl.TwitterSocialStreaming;


@SpringBootApplication
@ComponentScan(basePackages="workgroup.app.*")
public class DataplatformSocialApplication {
	
	@Autowired
	private TwitterSocialStreaming twitterSocialStreaming;
	
	public static void main(String[] args) {
		SpringApplication.run(DataplatformSocialApplication.class, args);
	}

	
	@PostConstruct
	public void initApp(){
		twitterSocialStreaming.startListening();
	}
}

