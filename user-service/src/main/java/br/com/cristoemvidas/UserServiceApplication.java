package br.com.cristoemvidas;

import br.com.cristoemvidas.service.SecurityHelper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;


@RestController
@SpringBootApplication
public class UserServiceApplication {

	public static void main(String[] args) {

		SpringApplication.run(UserServiceApplication.class, args);
	}

	@RequestMapping("/homeUser")
	public String hello() {
		System.out.println("principal = " + SecurityHelper.userData());
		return "Hello buddy!";


	}

	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@RequestMapping("/homeUser2")
	public String hello2(Principal principal) {
		System.out.println("principal = " + SecurityHelper.userData());
		return "Hello buddy!";
	}




}
