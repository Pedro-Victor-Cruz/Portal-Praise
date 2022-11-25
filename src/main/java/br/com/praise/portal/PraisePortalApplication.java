package br.com.praise.portal;

import br.com.praise.portal.model.User;
import br.com.praise.portal.repository.user.UserRepository;
import br.com.praise.portal.service.segurity.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PraisePortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(PraisePortalApplication.class, args);
	}

}
